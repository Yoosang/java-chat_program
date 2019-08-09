package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import client.ChatClient;

public class ClientManagerThread extends Thread{
	private Socket clientSocket;
	private String user_ID;
	
	public void setSocket(Socket _socket) {
		this.clientSocket = _socket;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter messageWriter = new PrintWriter(clientSocket.getOutputStream());
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
					System.out.println(user_ID+" 님이 입장했습니다.");
				}
				else if(splitedMessage[0].equals("message")) {
					send(user_ID + ">>" + splitedMessage[1]);
				}
				else if(splitedMessage[0].equals("quit")) {
					exit(messageWriter);
					break;
				}	
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void joinChat(String name, PrintWriter writer) {
		this.user_ID = name;
		String alram = user_ID + "이(가) 입장했습니다.";
		send(alram);
		synchronized (ChatServer.messageList) {
			ChatServer.messageList.add(writer);
		}
	}
	
	private void exit(PrintWriter writer) {
		synchronized (ChatServer.messageList) {
			ChatServer.messageList.remove(writer);
		}
		String alarm = user_ID + "이(가) 나갔습니다.";
		send(alarm);
	}
	
	private void send(String text) {
		synchronized (ChatServer.messageList) {
			System.out.println(ChatServer.messageList.size());
			for(int i = 0; i < ChatServer.messageList.size(); i++) {
				ChatServer.messageList.get(i).println(text);
				ChatServer.messageList.get(i).flush();
			}
		}
	}
}

