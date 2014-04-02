<?php
require_once 'core/init.php';
if(Input::get('username') != ""){
	$db = DB::getInstance();
	$user = $db->get('users',array('username','=',Input::get('username')))->first();
	echo "<p>Username: " . $user->username . "</p>";
	echo "<p>Name: " . $user->name . "</p>";
	echo "<p>Date Joined: " . $user->date . "</p>";
	echo "<p>Group: " . $user->group . "</p>";

}
