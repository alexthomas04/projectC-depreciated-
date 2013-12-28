<?php
require_once 'core/init.php';
if(Session::exists('home')){
	echo '<p>' . Session::flash('home') . '</p>';
}

$user = new User();
if($user->isLoggedIn()){
	?>
	<p>Hello <a href='#'> <?php echo escape($user->data()->username); ?></a></p>
	<ul>
		<li><a href="logout.php">log out</a></li>
		<li><a href="changepassword.php">Change Password</a></li>
	</ul>
	<?php
}else{
	?>
	<p>You need to <a href="login.php">login</a> or <a href="register.php">register</a></p>
	<?php
}
?>


<html>

	<head>
		<link rel="stylesheet" href="css/index.css" type="text/css" />
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	</head>
	<body>
		<div id='chat_area'>
			<div class='text_area'>

			</div>
			<form onsubmit='handleChat()' id='input_area' action='#'>
				<input class='chat_input' type='text'><button type='submit'>Send</button>
				<input type="submit" style="position: absolute; left: -9999px"/>
			</form>
		</div>






		<script type="text/javascript">

		$(document).ready(function(){

			$('.chat_input').on('submit',function(){
				handleChat();
			});


		});

		function handleChat(){
				console.log('tets');
				var text = $('.chat_input').val();
				var preparedMessage = prepareChat();
				var username=<?php echo "\"" . (($user->isLoggedIn())? $user->data()->username : 'NONAME') . "\"";	?>;
				var newText = "<p><span class='username'>"+username+":</span>  "+text+"</p>";
				$('.text_area').append(newText);
				$('.chat_input').val("");
				$.ajax({
					url: 'chat.php',
					type: 'post',
					dataType: 'string',
					data: {param1: 'value1'},
					success: function(data){updateChat( data )}
				})
				.done(function() {
					console.log("success");
				})
				.fail(function() {
					console.log("error");
				})
				.always(function() {
					console.log("complete");
				});
				


		}

		function prepareChat(){
			//format the chat for the ajax-php
			return $('.chat_input').val();
		}

		function updateChat(data){
			//logic for chat
			$('body').append(data);

		}
		</script>
	</body>

</html>
