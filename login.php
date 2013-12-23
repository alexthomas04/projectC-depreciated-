<?php
require_once 'core/init.php';

if(Input::exists()){
	if(Token::check(Input::get('token'))){
		$validate = new Validate();
		$validation = $validate->check($_POST,array(
			'username' => array('required'=>true),
			'password' => array('required'=>true)
			));

		if($validation->passed()){
			$user = new User();
			$remember = (Input::get('remember') === 'on') ? true : false;
			$login = $user->login(Input::get('username'),Input::get('password'),$remember);
			Redirect::to('index.php');
		}
		else{
			foreach ($validation->errors() as $error) {
				echo $error,'<br>';
			}
		}

	}
}
?>



<form action="" method="post">
	<div class ="field">
		<label for="username">Username</label>
		<input type="text" name="username" id="username" value="" autocomplete="on"></input>
	</div>
	<div class="field">
		<label for="password">Enter your Password</label>
		<input type="password" name="password" id="password" value="" ></input>
	</div>
	<div>
		<label for="remember">
			<input type="checkbox" name="remember" id="remember"> Remember me
		</label>

	</div>
	<input type="hidden" name="token" value="<?php echo Token::generate() ?>">
	<input type="submit" value="Log In">
</form>