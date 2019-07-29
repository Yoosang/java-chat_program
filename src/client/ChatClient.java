package client;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private final static String IP = "localhost";
	private final static int PORT_NUMBER = 8888;
	public static String userID;
	
	public static void main(String args[]) {
		try {
			Socket c_socket = new Socket(IP, PORT_NUMBER);
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}

