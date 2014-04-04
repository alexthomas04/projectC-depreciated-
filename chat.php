<?php
require_once 'core/init.php';
$log = array();
$message = Input::get('message');  
$_db = DB::getInstance();
$user = new User();
if(substr($message, 0,1)==="/"){
	
	$r = Communication::sendCommand(substr($message,1),$user);
	$log = array("type"=> "internal","time"=>date('H:i:s e',time()),"message"=>$message,"response"=>$r);
	//$log = array("type"=> 'internal',"message"=>$message,'response'=>'Sorry the game server is offline at this time',"time"=>date('H:i:s e',time()));
	}
	else{

			if($message!=null){
				$reg_exUrl = "/(http|https|ftp|ftps)\:\/\/[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(\/\S*)?/";
	    		 $message = htmlentities(strip_tags($message));
	     		if (($message) != "\n") {
	      		 if (preg_match($reg_exUrl, $message, $url)) {
	        		  $message = preg_replace($reg_exUrl, '<a href="'.$url[0].'" target="_blank">'.$url[0].'</a>', $message);
	      		 } 
	      		 	
	        		
	        		$chat_item = array(
	        		  	'message'=>$message,
	        		  	'name_color'=>$user->data()->color,
	        		  	'username'=>$user->data()->username,
	        		  	'time'=> date('H:i:s e',time()));
	      		 	$_db->insert('chat',$chat_item);


			}
		}
			//$lines = file('chat.txt'); -- replace lines from database
			// 
			$line_count = $_db->tableCount('chat');
			$log['state'] = $line_count;
			$state = (int)Input::get('state');
			if($state<0){
				$state=$line_count-1;
			}
			if($state!= $line_count ){
				$text = array();
                 	$chats = getMessages($state);
                 	foreach($chats as $chat){
                 		$username = $chat->username;
                 		$color=$chat->name_color;
                 		$t = $chat->message;
                 		$time=$chat->time;
                 		$text[]= '<p>'.
                 		'<span class="time">' . $time . "</span> " .
                 		'<a class="username" style="color:' . $color . '" href="profile.php?username=' . $username . '" target="_blank">' . $username . "</a>: " .
                 		$t . "</p>";
                       
              
                 }
             $log["text"] = $text;
         }
		
}

function getMessages($offset){
	$db = DB::getInstance();
	$limit = $db->tableCount('chat')-$offset;
	$messages = $db->getAll('chat','DESC',$limit)->results();
	return array_reverse($messages);
}
function getLastMessage(){
	$db = DB::getInstance();
	$r =$db->getAll('chat','DESC',1);
    return $r->first();
}
echo json_encode($log);
?>