<?php
require_once 'core/init.php';
$db = DB::getInstance();
$r = $db->tableCount('users');
echo var_dump($r);
?>

