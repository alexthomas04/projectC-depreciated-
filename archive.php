<?php
require_once 'core/init.php';
$user = new User();
if($user->isAdmin()){
	$lines = file('archive.txt');
			$line_count = count($lines);
				$text = array();
				foreach ($lines as $line_num => $line) {
                       echo $line . '<br />';
              
             }
}
else {
	echo '<h1>ACESS DENIED:ADMIN ONLY</h1>';
}