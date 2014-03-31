package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {
	private DatagramSocket listenSocket,writeSocket;
	private static final int LISTEN_PORT=9876;
	private static final int WRITE_PORT=9875;
	public Server() {
		try {
			listenSocket = new DatagramSocket(LISTEN_PORT);
			writeSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMessage(){
		String message="";
		do{
		byte[] reciveData = new byte[4000];
		DatagramPacket recivePacket = new DatagramPacket(reciveData,reciveData.length);
		try {
			listenSocket.receive(recivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message=new String(recivePacket.getData());
		System.out.println(message);
		}while(message.substring(0,1).equals("s"));
	
		return message;
	}
	
	public void sendMessage(String message){
		byte[] sendData = message.getBytes();
		DatagramPacket sendPacket;
		try {
			sendPacket = new DatagramPacket(sendData,sendData.length,InetAddress.getByName("127.0.0.1"),WRITE_PORT);
			writeSocket.send(sendPacket);
			System.out.println(message);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
