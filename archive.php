<?php
require_once 'core/init.php';
$user = new User();
$per_page=20;
if($user->isAdmin()){
	$db=DB::getInstance();
	$num_chats = $db->tableCount('chat');
	$page = (Input::get('page')!="")?Input::get('page'):1;
	$pages = ceil($num_chats/$per_page);
	if($page>$pages || $page<=0){
		echo 'Invalid Page Number';
	}
	else
	{$chats = $db->getAll('chat',null,$per_page*($page-1),$per_page*$page)->results();
		echo $per_page*$page . "   " . $per_page*($page-1);
		foreach ($chats as $key => $chat) {
			$username = $chat->username;
	                 		$color=$chat->name_color;
	                 		$t = $chat->message;
	                 		$time=$chat->time;
	                 		echo  '<p>'.
	                 		'<span class="time">' . $time . "</span> " .
	                 		'<a class="username"  href="profile.php?username=' . $username . '" target="_blank">' . $username . "</a>: " .
	                 		$t . "</p>";
					
			}}
			if($page>1){
				echo "<a href='archive.php?page=" . ($page-1) . "'> prev </a>";
			}
			for ($i=1; $i <=$pages ; $i++) { 
				if($i==$page){
					echo "<b> " . $i . "</b>"; 
				}
				else{
					echo "<a href='archive.php?page={$i}'> {$i}</a>";  
				}
			}
			if($page<$pages){
				echo "<a href='archive.php?page=" . ($page+1) . "'> next </a>";
			}

}
else {
	echo '<h1>ACESS DENIED:ADMIN ONLY</h1>';
}