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
					'unique' => 'users',
					'alpha_numeric' => true
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
							'username' => strtolower(Input::get('username')),
							'password' => Hash::make(Input::get('password'),$salt),
							'salt' => $salt,
							'name' => Input::get('name'),
							'date' => date('Y-m-d H:i:s'),
							'group' => 1
						));
						Session::flash('home','You have been registered and can now log in!');
						$user->login(Input::get('username'),Input::get('password'));
						header('Location: index.php');
					}
					catch(Exception $e){
						die($e->getMessage());
					}
			}
			else{
				$errors="";
				$x=1;
				foreach($validation->errors() as $error){
					$errors .= $error;
					if(count($validation->errors())>$x){
						$errors .= ',';
					}
					$x++;
				}
				?>
				<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
				<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
				<link rel="stylesheet" href="css/register.css" type="text/css" />
					<script type="text/javascript">
					$(document).ready(function(){
						var string = <?php echo "\"" . escape($errors) . "\"" ?>;
						console.log(string);
						var parts = string.split(',');
						parts.forEach(function(entry) {
   							 var id=entry.split('|')[0];
   							 var error=entry.split('|')[1];
   							 $('#'+id).after("<p class='error'>"+ error + "</p>");
   							 console.log(id + "," + error);
						});
						$('.error').animate({
							left: '10px',
							opacity: '1'},
							600, function() {
							/* stuff to do after animation is complete */
						});
					});
					</script>


					<?php
			}}
}
?>

		
<form id="register" action="" method="post">
	<div class ="field">
		<label for="username">Account Name</label>
		<input type="text" name="username" id="username" value="<?php echo escape(Input::get('username')); ?>" autocomplete="off"></input>
	</div>
	<div class="field">
		<label for="password">Password</label>
		<input type="password" name="password" id="password" value="" ></input>
	</div>
	<div class="field">
		<label for="password_again">Password (Again)</label>
		<input type="password" name="password_again" id="password_again" value="" ></input>
	</div>
	<div class="field">
		<label for="name">Character Name</label>
		<input type="text" name="name" id="name" value="<?php echo escape(Input::get('name')); ?>" ></input>
	</div>
	<input type="hidden" name="token" value="<?php echo Token::generate() ?>">
	<input type="submit" value="register">
</form>