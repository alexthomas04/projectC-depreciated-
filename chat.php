<?php
require_once 'core/init.php';
$log = array();
$message = Input::get('message'); 
$guid = Input::get('guid'); 
$_db = DB::getInstance();
$user = new User();
if(substr($message, 0,1)==="/"){
	
	$r = Communication::sendCommand(substr($message,1),$user);
	$log = array("type"=> "internal","time"=>date('H:i:s T',time()),"message"=>$message,"response"=>$r);
	//$log = array("type"=> 'internal',"message"=>$message,'response'=>'Sorry the game server is offline at this time',"time"=>date('H:i:s e',time()));
	}
	else if(substr($message, 0,1)==="."){
		$parts = explode(" ", $message);
		$command = substr($parts[0],1);
		switch ($command) {
			case 'color':
				$user->change('color',escape($parts[1]));
				break;
			case 'whisper':
				if(count($parts)>=3)
				{
					$revicpiant = new User($parts[1]);
					if($revicpiant->exists())
					{
						$message = "";
						for ($i=2; $i <$parts.count() ; $i++) { 
							$message .= $parts[$i];
						}
						$_db->insert('private_chat',array(
							'from_username'=>$user->data()->username,
							'time'=> date('H:i:s T',time()),
							'to_username'=>escape($parts[1]),
							'message'=>escape($message),
							'type'=>'whisper'));
						$revicpiant->change('has_private',1);
						$log['text'][] = "sent whisper";
					}
					else{
						$log['text'][] = "<span class='chat_error'>User {$parts[1]} does not exist</span>";
					}
				}
				else{
					$log['text'][] = "<span class='chat_error'>Invalid number of arguments</span>";
				}
				break;
			case 'commands':
				$log['text'][]="<i>color, whisper, commands, help, ?</i>";
				break;
			case 'help':
				$log['text'][]="type '.' then a command <i>commands</i> for a list of commands";
				break;
			case '?':
				$log['text'][]="type '.' then a command. <i>.Commands</i> for a list of commands";
			break;
			default:
				# code...
				break;
		}

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
	        		  	'time'=> date('H:i:s T',time()),
	        		  	'guid'=>$guid);
	      		 	$_db->insert('chat',$chat_item);


			}
		}
			
			$line_count = $_db->tableCount('chat');
			$log['state'] = $line_count;
			$state = (int)Input::get('state');
			$text = array();
			if($state<0){
				$state=$line_count;
			}
			if($user->data()->has_private){
				$privates = $_db->get('private_chat',array('to_username','=',$user->data()->username))->results();
				$game_messages = $_db->get('game_messages',array('to_user_id','=',$user->data()->id))->results();
				$user->change('has_private',0);
				foreach ($privates as $private) {
					$from_username = $private->from_username;
                 	$t = $private->message;
               		$time=$private->time;
               		$type = $private->type;
					$text[] = "<span class='private'><span class='time'>{$time}</span> <span class='private_type'>{$type}</span> <span class='private_from'>{$from_username}</span>: <span class='{$type}'>{$t}</span></span>" ;
					$_db->delete('private_chat',array('id','=',$private->id));
				}
				foreach ($game_messages as $game_message) {
					$message=$game_message->message;
					$time=$game_message->time;
					$text[] = "<span class='time'>{$time}</span><span class='game_message'>{$message}</span>";
					$_db->delete('game_messages',array('id','=',$game_message->id));
				}
			}

			if($state!= $line_count ){
                 	$chats = getMessages($state);

                 	foreach($chats as $chat){
                 		$username = $chat->username;
                 		$color=$chat->name_color;
                 		$t = $chat->message;
                 		$time=$chat->time;
                 		$text[]= array(
                 			'message'=>	'<span class="time">' . $time . "</span> " .
                 				'<a class="username" style="color:' . $color . '!important" href="profile.php?username=' . $username . '" target="_blank">' . $username . "</a>: " .
                 				$t,
                 			'guid'=>$chat->guid 
                 		);
                       
              	
              	}
         }
          $log["text"] = $text;
		
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