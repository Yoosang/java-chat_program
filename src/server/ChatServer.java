package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	
	private final static int PORT_NUMBER = 8888; 
	public static ArrayList<PrintWriter> message_outputList;
	
	public static void main(String[] args) {
		
		message_outputList = new ArrayList<PrintWriter>();
		
		try {
			ServerSocket server_socket = new ServerSocket(PORT_NUMBER);
			
			while(true) {
				Socket client_socket = server_socket.accept();
				ClientManagerThread client_manager_thread = new ClientManagerThread();
				client_manager_thread.setSocket(client_socket);
				
				message_outputList.add(new PrintWriter((client_socket.getOutputStream())));
				client_manager_thread.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}	
	}
}