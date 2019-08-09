package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.io.PrintWriter;

public class ChatServer {
	private final static int PORT_NUMBER = 8888;
	public static ArrayList<PrintWriter> messageList;
	public static void main(String[] args) {
		messageList = new ArrayList<PrintWriter>();
		
		try {
			ServerSocket server_socket = new ServerSocket();
			String hostIP = InetAddress.getLocalHost().getHostAddress();
			server_socket.bind(new InetSocketAddress(hostIP, PORT_NUMBER));
			System.out.println("Run server!\n" + "IP: " + hostIP + ", Port: " + PORT_NUMBER);
			
			while(true) {
				Socket client_socket = server_socket.accept();
				ClientManagerThread client_manager_thread = new ClientManagerThread();
				client_manager_thread.setSocket(client_socket);
				client_manager_thread.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}	
	}
}