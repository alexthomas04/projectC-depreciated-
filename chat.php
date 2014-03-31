<?php
require_once 'core/init.php';
$user = new User();
$message = Input::get('message');
echo $user->data()->username . ':' . $message . '<br />';  
if(substr($message, 0,1)==="/"){
	Communication::sendCommand(substr($message,1),$user);
	}

?>