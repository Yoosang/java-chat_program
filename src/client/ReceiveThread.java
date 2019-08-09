package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{
	private Socket messageSocket;
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader recieveBuf = new BufferedReader(new InputStreamReader(messageSocket.getInputStream()));
			String receiveString;
			
			while(true) {
				receiveString = recieveBuf.readLine();
				System.out.println(receiveString);
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket) {
		this.messageSocket = _socket;
	}
}
