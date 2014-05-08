<?php
echo 'hello';
	require_once 'core/init.php';
$user = new User();
if($user->isLoggedIn()){
	if(Input::exists() && Token::check(Input::get('token'))){
		$validate = new Validate();
		$validation = $validate->check($_POST,array(
			'current_password'=>array(
				'required' => true
			),
			'new_password'=>array(
				'required' => true,
				'min' =>6,
				'alpha_numeric'=> true,
				'max'=>32
			),
 			'new_password_again'=>array(
				'required'=>true,
				'matches'=>'new_password'
			)
		));

		if($validation->passed()){
			echo $user->data()->username . ' - ' . Input::get('current_password') . ' - ' . Hash::make(Input::get('current_password'),$user->data()->salt);
			if($user->login($user->data()->username,Input::get('current_password'))){
				$new_salt = Hash::salt(32);
				$user->change('password',Hash::make(Input::get('new_password'),$new_salt));
				$user->change('salt',$new_salt);

				
			}else{ #current passoword does not match
				echo "<p class='error'> You entered the wrong password</p>";
			}
		}else{  #validation dod not pass
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
				<link rel="stylesheet" href="register.css" type="text/css" />
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
		}
	}
?>



<html>
	<head>
	<link rel="stylesheet" href="css/login.css" type="text/css" />
	</head>
	<body>
		<div id="banner">&nbsp;</div>
		<form id="changePassword" action="" method="post">
			<div class="field">
				<label for="current_password">Current Password</label>
				<input type="password" id="current_password" name="current_password" value=""></input>
			</div>
			<div class="field">
				<label for="new_password">New Password</label>
				<input type="password" id="new_password" name="new_password" value=""></input>
			</div>
			<div class="field">
				<label for="new_password_again">Confirm Password</label>
				<input type="password" id="new_password_again" name="new_password_again" value=""></input>
			</div>
			<input type="hidden" name="token" value="<?php echo Token::generate() ?>">
			<input type="submit" value="Change">
		</form>
	</body>
</html>
<?php
}
else{
	?>
	<p>You need to <a href="login.php">login</a> or <a href="register.php">register</a></p>
	<?php
}
?>