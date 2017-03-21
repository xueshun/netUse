package bhz.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(PORT);
			System.out.println(" server start ... ");
			//进行阻塞
			Socket socket = server.accept();
			//新建一个线程执行客户端的任务
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
