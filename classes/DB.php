<?php
class DB{
	private static $_instance = null;
	private $_pdo= null, 
			$_query,
			$_error = false ,
			$_results,
			$_count=0;

	private function __construct(){
		try{
			$this->_pdo = new PDO('mysql:host=' . Config::get('mysql/host') .';dbname=' . Config::get('mysql/db') ,Config::get('mysql/username'),Config::get('mysql/password') );
			$this->_pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		}
		catch(PDOEception $e){
			die($e-> getMessgae());
		}
	}

	public static function getInstance(){
		if(!isset(self::$_instance)){
			self::$_instance = new DB();
		}
		return self::$_instance;
	}

	public function query($sql,$params = array()){

		$this->_error = false;
		$x=1;
		if($this->_query = $this->_pdo->prepare($sql)){
			if(count($params)){
				foreach($params as $param){
					$this->_query->bindValue($x, $param);
					$x++;
				}

			}
			if($this->_query->execute()){
				try{
					$this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
					$this->_count = $this->_query->rowCount();
				}
				catch(Exception $e){

				}
			}else{
				$this->_error=true;
			}

		}
		return $this;

	}

	public function error(){
		return $this->_error;
	}

	public function action($action , $table, $where = array()){
		if(count($where)==3){
			$operators = array('=','>','<','>=','<=');

			$field=$where[0];
			$operator=$where[1];
			$value=$where[2];

			if(in_array($operator, $operators)){
				$sql="{$action} FROM {$table}  WHERE {$field} {$operator} ?";

				if(!$this->query($sql,array($value))->error()){
					return $this;

				}
			}
		}
		return false;
	}
	public function getAll($table,$order,$limit=0,$end=0){
		$sql = "SELECT * FROM " . $table ."\n";
		if(isset($order)){$sql=$sql . " ORDER BY `{$table}`.`id` {$order} \n";}
		if($limit != 0){ $sql=$sql. " LIMIT " . $limit;
			if($end != 0){
				$sql = $sql . ", " . $end;
			}
			$sql = $sql . "\n";
	}
		
		if(!$this->query($sql,null)->error()){
					return $this;

				}
	}

	public function get($table,$where){
		return $this->action('SELECT *', $table,$where);
	}
	public function delete($table,$where){
		return $this->action('DELETE', $table,$where);
	}

	public function count(){
		return $this->_count;
	}

	public function results(){
		return $this->_results;
	}

	public function first(){
		return $this->_results[0];
	}

	public function insert($table,$fields = array()){
		if(count($fields)){
			$keys = array_keys($fields);
			$values = '';
			$x=1;

			foreach($fields as $field){
				$values .= '?';
				if($x < count($fields)){
					$values .= ', ';
				}
				$x++;
			}
			$sql = "INSERT INTO `{$table}` (`" . implode('`, `', $keys) . "`) VALUES ({$values})";
			if(!$this->query($sql, $fields)->error()){
				return true;
				
			}

		}
		return false;

	}

	public function update($table, $id, $fields=array()){
		$set = '';
		$x=1;

		foreach($fields as $name=> $value){
			$set .= "{$name} = ?";
			if($x < count($fields)){
				$set .= ', ';
			}
		}

		$sql="UPDATE {$table} SET {$set} WHERE id={$id}";
		if(!$this->query($sql,$fields)->error()){
			return true;
		}
		return false;
	}

	public function tableCount($table){
		$sql="SELECT COUNT(*) FROM ".$table;
		if(!$this->query($sql,null)->error()){
					$array_form = get_object_vars($this->first());
					return $array_form['COUNT(*)'];

				}
	}

}


