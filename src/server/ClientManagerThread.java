package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManagerThread extends Thread{
	private Socket m_socket;
	private String user_ID;
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader tempBuf = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			String text;
			
			while(true) {
				text = tempBuf.readLine();
				
				if(text == null) {
					System.out.println(user_ID + "이(가) 나갔습니다.");
					for(int i = 0; i<ChatServer.message_outputList.size(); i++) {
						ChatServer.message_outputList.get(i).println(user_ID+ "이(가) 나갔습니다.");
						ChatServer.message_outputList.get(i).flush();
					}
					break;
				}
				
				String[] split = text.split("abcd123");
				if(split.length == 2 && split[0].equals("ID")) {
					user_ID = split[1];
					System.out.println(user_ID + "이(가) 입장하셨습니다.");
					for(int i = 0; i<ChatServer.message_outputList.size(); i++) {
						ChatServer.message_outputList.get(i).println(user_ID + "이(가) 입장하셨습니다.");
						ChatServer.message_outputList.get(i).flush();
					}
					continue;
				}
				
				for(int i = 0; i<ChatServer.message_outputList.size(); i++) {
					ChatServer.message_outputList.get(i).println(user_ID + ">> " + text);
					ChatServer.message_outputList.get(i).flush();
				}
			}
			ChatServer.message_outputList.remove(new PrintWriter(m_socket.getOutputStream()));
			m_socket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket) {
		m_socket = _socket;
	}
}
