<?php
require_once 'core/init.php';
if(Session::exists('home')){
	echo '<p>' . Session::flash('home') . '</p>';
}

$user = new User();
if($user->isLoggedIn()){
	?>
	<p>Hello <a href='#'> <?php echo escape($user->data()->username); ?></a></p>
	<?php
}