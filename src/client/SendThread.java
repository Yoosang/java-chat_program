package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;

public class SendThread extends Thread {
	private Socket m_socket;
	
	@Override
	public void run() {
		
		super.run();
		try {
			BufferedReader tempBuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(m_socket.getOutputStream());
			
			String sendString;
			
			System.out.println("사용할 ID를 입력해주세요 : ");
			ChatClient.userID = tempBuf.readLine();
			
			sendWriter.println("IDabcd123" + ChatClient.userID);
			sendWriter.flush();
			
			while(true) {
				sendString = tempBuf.readLine();
				if(sendString.equals("exit")) {
					break;
				}
				
				sendWriter.println(sendString);
				sendWriter.flush();
			}
			
			sendWriter.close();
			tempBuf.close();
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

