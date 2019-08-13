package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;

public class SendThread extends Thread {
	private Socket messageSocket;
	
	@Override
	public void run() {
		
		super.run();
		try {
			BufferedReader recieveBuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(messageSocket.getOutputStream());
			
			String inputString;
			
			System.out.print("사용할 ID를 입력해주세요 : ");
			ChatClient.userID = recieveBuf.readLine();
			 
			sendWriter.println("enter>>" + ChatClient.userID);
			sendWriter.flush();

			
			while(true) {
				inputString = recieveBuf.readLine();
				if(inputString.equals("exit")) {
					sendWriter.println("quit>>" + recieveBuf);
					sendWriter.flush();
					break;
				}
				
				sendWriter.println("message>>" + inputString);
				sendWriter.flush();
			}
			
			sendWriter.close();
			recieveBuf.close();
			messageSocket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void setSocket(Socket _socket) {
		this.messageSocket = _socket;
	}
}

