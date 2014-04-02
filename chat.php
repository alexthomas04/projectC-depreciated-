<?php
require_once 'core/init.php';
$log = array();
$message = Input::get('message');  
if(substr($message, 0,1)==="/"){
	
	//$r = Communication::sendCommand(substr($message,1));
	//$log = array("type"=> "internal","time"=>date('H:i:s e',time()),"message"=>$message,"response"=>$r);
	$log = array("type"=> 'internal',"message"=>$message,'response'=>'Sorry the game server is offline at this time',"time"=>date('H:i:s e',time()));
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
	      		 	
	        		  fwrite(fopen('chat.txt', 'a'), '<span class="time">' . date('H:i:s e',time()) . '</span> ' . '<a href="profile.php?username=' . Session::get('username') . '" target="_blank"><span class="username">' .   Session::get('username') . '</span></a>: ' . $message = str_replace("\n", " ", $message) . "\n"); 

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
                 	fwrite(fopen("archive.txt", "a"), $line );
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