package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManagerThread extends Thread{
	private Socket clientSocket;
	private String user_ID;
	private ArrayList<PrintWriter> messageList;
	
	public void setSocket(Socket _socket) {
		clientSocket = _socket;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			messageList = new ArrayList<PrintWriter>();
			BufferedReader messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter messageWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			String message;
			 
			while(true) {
				message = messageReader.readLine();
				
				if(message == null) {
					exit(messageWriter);
					break;
				}
				
				String[] splitedMessage = message.split(">>");
				
				if(splitedMessage[0].equals("enter")) {
					joinChat(splitedMessage[1], messageWriter);
				}
				else if(splitedMessage[0].equals("message")) {
					send(user_ID + ">>" + splitedMessage[1]);
				}
				else if(splitedMessage[0].equals("quit")) {
					exit(messageWriter);
				}	
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void joinChat(String name, PrintWriter writer) {
		name = this.user_ID;
		String data = name + "이(가) 입장했습니다.";
		send(data);
		
		synchronized (messageList) {
			messageList.add(writer);
		}
	}
	
	private void exit(PrintWriter writer) {
		synchronized (messageList) {
			messageList.remove(writer);
		}
		String alarm = this.user_ID + "이(가) 나갔습니다.";
		send(alarm);
	}
	
	private void send(String text) {
		synchronized (messageList) {
			for(int i = 0; i < messageList.size(); i++) {
				messageList.get(i).println(text);
				messageList.get(i).flush();
			}
		}
	}
}

