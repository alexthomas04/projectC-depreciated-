<?php
class Validate{
	private $_passed = false,
			$_errors = array(),
			$_db = null;

	public function __construct(){
		$this->_db=DB::getInstance();
	}

	public function check($source,$items = array()){
		foreach($items as $item=>$rules){
			foreach($rules as $rule => $rule_value){

				$value = trim($source[$item]);

				if($rule == 'required' && empty($value)){
					$this->addError("{$item}| {$item} is required");
				}else
				{
					switch ($rule) {
						case 'min':
							if(strlen($value)<$rule_value){
								$this->addError("{$item}| {$item} must be a minimum of {$rule_value} characters");
							}
						break;
						case 'max':
							if(strlen($value)>$rule_value){
								$this->addError("{$item}| {$item} must be a maximun of {$rule_value} characters");
							}
						break;
						case 'matches':
							if($value != $source[$rule_value]){
								$this->addError("{$item}| {$item} must be match {$rule_value}");
							}
						break;
						case 'unique':
							$check= $this->_db->get($rule_value,array($item,'=',strtolower($value)));
							if($check->count()){
								$this->addError("{$item}| {$item} already exists");
							}
						break;

						case 'alpha_numeric':
							if(is_numeric($value)){
								$this->addError("{$item}| {$item} must be more than just numbers");
							}
						break;
						
						default:
							# code...
						break;
					}
				}

			}

		}
		if(empty($this->_errors)){
			$this->_passed=true;
		}
		else
		{
			$this->_passed=false;
		}
		return $this;
	}

	private function addError($error){
		$this->_errors[] = $error;

	}
	public function errors(){
		return $this->_errors;
	}
	public function passed(){
		return $this->_passed;
	}
}