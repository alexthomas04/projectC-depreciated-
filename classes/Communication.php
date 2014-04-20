<?php
class Communication{
		private static $listen_Port=9875;
		private static $write_Port=9876;
	public static function sendCommand($command,$user){
			if(!($user)){
			return "";
		}
		$username = $user->data()->username;
		$onServer = $user->data()->onServer;
		if(!($write_socket= socket_create(AF_INET, SOCK_DGRAM, 0))){
		$errorCode = socket_last_error();
		$errorMsg = socket_strerror($errorCode);
		die("Could not create socket: [$errorcode] $errorMsg \n");
	}
	if(!socket_bind($write_socket,"127.0.0.1",1235)){
		$errorCode = socket_last_error();
		$errorMsg = socket_strerror($errorCode);
		die("Could not bind socket: [$errorcode] $errorMsg \n");
	}
	$guid = uniqid();
	$prepared = "c" . "|" . $guid . "|" . "execute" . "|" . $username . "|" . $command;
	 socket_sendto($write_socket,$prepared,4000,0,"127.0.0.1",9876);
	return Communication::waitForResponse($guid); 
	}

	private static function waitForResponse($guid){
		if(!($listen_socket= socket_create(AF_INET, SOCK_DGRAM, 0))){
		$errorCode = socket_last_error();
		$errorMsg = socket_strerror($errorCode);
		die("Could not create socket: [$errorcode] $errorMsg \n");
	}
	if(!socket_bind($listen_socket,"127.0.0.1",9875)){
		$errorCode = socket_last_error();
		$errorMsg = socket_strerror($errorCode);
		die("Could not bind socket: [$errorcode] $errorMsg \n");
	}
		$found = false;
		// while(!$found){
		//socket_recvfrom($listen_socket, $buf, 4000, 0, "localhost",9875);
		socket_set_option($listen_socket,SOL_SOCKET,SO_RCVTIMEO,array("sec"=>2,"usec"=>0));
		$result = socket_recv($listen_socket,$buf,4000,0);

		if(isset($buf) && $buf!=null){
		$parts = explode("|", $buf);
		return $parts[count($parts)-1];
	}else
	{
		return "Sorry the game server is offline at this time";
	}
		/*if($parts[0]=='s' && $parts[1]==$guid){
				$found=true;
				return $parts;
		}*/
		// }
	}
}
?>