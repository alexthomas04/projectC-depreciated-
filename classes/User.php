<?php
class User{
	private $_db,
			$_data,
			$_sessionName,
			$_cookieName,
			$_isLoggedIn,
			$_permissions;

	public function __construct($user=null){
		$this->_db=DB::getInstance();
		$this->_sessionName= Config::get('session/session_name');
		$this->_cookieName= Config::get('remember/cookie_name');
		if(!$user){
			if(Session::exists($this->_sessionName)){
				$user=Session::get($this->_sessionName);
				if($this->find($user)){
					$this->_isLoggedIn=true;
					
				}else{

				}
			}
		}else{
			$this->_isLoggedIn=$this->find($user);
			}
			if($this->_isLoggedIn){
				$this->_permissions = json_decode($this->_db->get('groups',array('id','=',$this->data()->group))->first()->permissions,true);
			}
		
	}

	public function create($fields=array()){
		if(!$this->_db->insert('users',$fields)){
			throw new Exception("There was a problem createing the account");
			
		}
	}

	public function find($user = null){
		if($user){
			$field = (is_numeric($user)) ? 'id' : 'username';
			$data = $this->_db->get('users',array($field,'=',$user));

			if($data->count()){
				$this->_data = $data->first();
				return true;

			}
		}
		return false;
	}

	public function login($username=null,$password=null,$remember=false){
		$user = $this->find($username);
		if(!$username && !$password && $this->exists()){
			Session::put($this->_sessionName,$this->data()->id);

		}
			else{
				if($user){
							if($this->data()->password === Hash::make($password,$this->data()->salt)){
								Session::put($this->_sessionName,$this->data()->id);
								if($remember){
									$hash = Hash::unique();
									$hashCheck= $this->_db->get('users_session',array('user_id','=',$this->data()->id));
			
									if(!$hashCheck->count()){
										$this->_db->insert('users_session',array(
											'user_id'=> $this->data()->id,
											'hash' => $hash
										));}
										else{
											$hash=$hashCheck->first();
										}
										Cookie::put($this->_cookieName,$hash,Config::get('remember/cookie_expiry'));
									}
								return true;
							}
							else{
								print_r('Wrong username or password');
							}
						}
					}


		return false;
	}

	public function data(){
		return $this->_data;
	}
	public function isLoggedIn(){
		return $this->_isLoggedIn;
	}

	public function logout(){
		$this->_db->delete('users_session',array('user_id','=',$this->data()->id));
		Session::delete($this->_sessionName);
		Cookie::delete($this->_cookieName);
	}
	public function exists(){
		return (!empty($this->_data)) ? true : false;
	}

	public function change($field , $value){
		$this->_db->update('users',$this->data()->id,array($field=>$value));
	}

	public function hasPermission($permission){
		return $this->_permissions[$permission];

	}
}