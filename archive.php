<?php
require_once 'core/init.php';
$user = new User();
$per_page;
if($user->hasPermission('archive')){
	if(Input::get('per_page')!=""){
		Cookie::put("per_page",Input::get('per_page'),3600*24*500);
		$per_page = Input::get('per_page');
	}else if(Cookie::exists('per_page')){
		$per_page = Cookie::get('per_page');
	}
	else{
		$per_page=20;
	}
	$db=DB::getInstance();
	$num_chats = $db->tableCount('chat');
	$page = (Input::get('page')!="")?Input::get('page'):1;
	$pages = ceil($num_chats/$per_page);
	if($page>$pages || $page<=0){
		Redirect::to('archive.php');
	}
	else
	{$chats = $db->getAll('chat',null,$per_page*($page-1)+1,$per_page)->results();
		echo "<ol>";
		foreach ($chats as $key => $chat) {
			$username = $chat->username;
	                 		$color=$chat->name_color;
	                 		$t = $chat->message;
	                 		$time=$chat->time;
	                 		if($color == 'white')
	                 			$color = "333";
	                 		echo  '<li>'.
	                 		'<span class="time">' . $time . "</span> " .
	                 		'<a class="username"  href="profile.php?username=' . $username . '" target="_blank" style="color:' . $color . '">' . $username . "</a>: " .
	                 		$t . "</li>";
					
			}}
			echo "</ol>";
			if($page>1){
				echo "<a href='archive.php?page=" . ($page-1) . "'> prev </a>";
			}
			$min=1;
			$max=$pages;
			if($page>5){
				$min=$page-5;
			}
			if($pages-$page>5){
				$max=$page+5;
			}
			for ($i=$min; $i <=$max ; $i++) { 
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
			echo '<br>';
			echo "<p>Per Page</p>";
			$values = array(20,50,100);
			foreach ($values as $key => $value) {
				echo "<a href='archive.php?page={$page}&per_page={$value}'> {$value}</a>";
			}

}
else {
	echo '<h1>ACESS DENIED:ADMIN ONLY</h1>';
}