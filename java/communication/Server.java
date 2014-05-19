package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * The Class Server.
 */
public class Server {
	
	/** The write socket. */
	private DatagramSocket listenSocket,writeSocket;
	
	/** The Constant LISTEN_PORT. */
	private static final int LISTEN_PORT=9876;
	
	/** The Constant WRITE_PORT. */
	private static final int WRITE_PORT=9875;
	
	/**
	 * Instantiates a new server.
	 */
	public Server() {
		try {
			listenSocket = new DatagramSocket(LISTEN_PORT);
			writeSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage(){
		String message="";
		do{
		byte[] receiveData = new byte[4000];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
		try {
			listenSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		message=new String(receivePacket.getData());
		System.out.println(message);
		}while(message.substring(0,1).equals("s"));
	
		return message;
	}
	
	/**
	 * Send message.
	 *
	 * @param message the message
	 */
	public void sendMessage(String message){
		message = message.replaceAll("\n", "<br />");
		byte[] sendData = message.getBytes();
		DatagramPacket sendPacket;
		try {
			int length=sendData.length;
			if(sendData.length>4000)
				length=4000;
			sendPacket = new DatagramPacket(sendData,length,InetAddress.getByName("127.0.0.1"),WRITE_PORT);
			writeSocket.send(sendPacket);
			System.out.println(message);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
