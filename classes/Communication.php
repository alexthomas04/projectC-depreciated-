<?php
class Communication{
		private static $listen_Port=9875;
		private static $write_Port=9876;
	public static function sendCommand($command){
		if(!(Session::exists('username') && Session::exists('onServer'))){
			return "";
		}
		$username = Session::get('username');
		$onServer = Session::get('onServer');
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
	if(!($onServer)){
		$guid = com_create_guid();
		$prepared = "c" . "|" . $guid . "|" . "add" . "|" . $username ;

		socket_sendto($write_socket,$prepared,4000,0,"127.0.0.1",9876);
		$user = new User();
		$user->change("onServer",true);
	}
	$guid = com_create_guid();
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
		$result = socket_recv($listen_socket,$buf,4000,0);
		$parts = explode("|", $buf);
		return $parts[count($parts)-1];
		/*if($parts[0]=='s' && $parts[1]==$guid){
				$found=true;
				return $parts;
		}*/
		// }
	}
}
?>