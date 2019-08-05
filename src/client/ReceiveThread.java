package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{
	private Socket m_socket;
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader tempBuf = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			
			String receiveString;
			String[] split;
			
			while(true) {
				receiveString = tempBuf.readLine();
				split = receiveString.split(">");
				if(split.length >= 2 && split[0].equals(ChatClient.userID)) {
					continue;
				}
				System.out.println(receiveString);
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket) {
		m_socket = _socket;
	}
}

