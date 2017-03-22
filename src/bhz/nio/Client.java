package bhz.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 * @author Administrator
 *
 */
public class Client {
	//需要一个selector来判断管段的状态
	public static void main(String[] args) {
		
		//1.创建连接地址
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);
		
		//2.声明连接通道
		SocketChannel sc = null;
		
		//3.建立缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		try {
			//4.打开通道
			sc = SocketChannel.open();
			//5.进行连接
			sc.connect(address);
			
			while(true){
				//定义一个字节数组，然后使用系统录入功能；
				byte[] bytes = new byte[1024];
				System.in.read(bytes);
				
				//把数据放到缓冲区中
				buf.put(bytes);
				//对缓冲区进行复位
				buf.flip();
				//写出数据
				sc.write(buf);
				buf.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(sc != null){
				try {
					sc.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
