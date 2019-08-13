package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
	private static String IP;
	private final static int PORT_NUMBER = 8888;
	public static String userID;
	
	public static void main(String args[]) {
		Socket clientSocket = new Socket();
		IP = inputIP();
		
		try {
			clientSocket.connect(new InetSocketAddress(IP, PORT_NUMBER));
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(clientSocket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(clientSocket);
			
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
	
	private static String inputIP() {
		String ret = null;
		BufferedReader ipBuf = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("IP를 입력하세요: ");
			ret = ipBuf.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}

