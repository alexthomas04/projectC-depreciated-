<?php
session_start();

$GLOBALS['config'] = array(
	'mysql' => array(
		'host' => 'fdb3.biz.nf',
		'username' => '1516730_m5',
		'password' => 'piRcircl3',
		"db" => '1516730_m5'
	),
	'remeber' => array(
		'cookie_name' => 'hash',
		'cookie_expiry' => 604800
	),
	'session' => array(
		'session_name' => 'user'
	)
);

spl_autoload_register(function($class){
	require_once 'classes/' . $class . '.php';
});

require_once 'functions/sanitize.php';