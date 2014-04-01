<?php
require_once 'core/init.php';
$user = new User();
$log = array();
$message = Input::get('message');  
if(substr($message, 0,1)==="/"){
	
	$r = Communication::sendCommand(substr($message,1),$user);
	$log = array("type"=> "internal","time"=>time('H-i-s-u-e'),"message"=>$message,"response"=>$r);
	}
	else{
		if(file_exists("chat.txt")){
			if($message!=null){
				$reg_exUrl = "/(http|https|ftp|ftps)\:\/\/[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(\/\S*)?/";
	    		 $message = htmlentities(strip_tags($message));
	     		if (($message) != "\n") {
	      		 if (preg_match($reg_exUrl, $message, $url)) {
	        		  $message = preg_replace($reg_exUrl, '<a href="'.$url[0].'" target="_blank">'.$url[0].'</a>', $message);
	      		 } 
	      		 	
	        		  fwrite(fopen('chat.txt', 'a'), '<span class="username">' .  $user->data()->username . ':</span>'. $message = str_replace("\n", " ", $message) . "\n"); 

			}
		}
			$lines = file('chat.txt');
			$line_count = count($lines);
			$log['state'] = $line_count;
			$state = Input::get('state');
			if($state!= $line_count){
				$text = array();
				foreach ($lines as $line_num => $line) {
                 if ($line_num >= $state){
                       $text[] =  $line = str_replace("\n", "", $line);
                 }
             }
             $log["text"] = $text;
             if(count($lines)>20){
	      		 		 file_put_contents("chat.txt", " ");
	      		 		 $log['state'] = 0;
	      		 	}
			}
		
	}
}
echo json_encode($log);
?>