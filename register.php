<?php
require_once 'core/init.php';


if(Input::exists()){
	if(Token::check(Input::get('token')))
		{$validate = new Validate();
			$validation = $validate->check($_POST,array(
				'username' =>array(
					'required' => true,
					'min' => 2,
					'max' => 20,
					'unique' => 'users'	
					),
				'password' => array(
					'required' => true,
					'min'=>6
					),
				'password_again' =>array(
					'required'=>true,
					'matches'=>'password'
					),
				'name'=>array(
					'required'=>true,
					'min'=>2,
					'max'=>50
					)
		
			));
		
			if($validation->passed()){
					$user = new User();
					$salt = Hash::salt(32);
					try{
						$user->create(array(
							'username' => Input::get('username'),
							'password' => Hash::make(Input::get('password'),$salt),
							'salt' => $salt,
							'name' => Input::get('name'),
							'date' => date('Y-m-d H:i:s'),
							'group' => 1
						));
					}
					catch(Exception $e){
						die($e->getMessage());
					}
			}
			else{
				print_r($validation->errors());
			}}
}
?>


<form action="" method="post">
	<div class ="field">
		<label for="username">Username</label>
		<input type="text" name="username" id="username" value="<?php echo escape(Input::get('username')); ?>" autocomplete="off"></input>
	</div>
	<div class="field">
		<label for="password">Enter your Password</label>
		<input type="password" name="password" id="password" value="" ></input>
	</div>
	<div class="field">
		<label for="password_again">Enter your Password again</label>
		<input type="password" name="password_again" id="password_again" value="" ></input>
	</div>
	<div class="field">
		<label for="name">name</label>
		<input type="text" name="name" id="name" value="<?php echo escape(Input::get('name')); ?>" ></input>
	</div>
	<input type="hidden" name="token" value="<?php echo Token::generate() ?>">
	<input type="submit" value="register">
</form>