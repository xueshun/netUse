package bhz.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{

	//1.多路复用器（管理所有的通道）
	private Selector selector;
	//2.建立缓冲区（读）
	private ByteBuffer readBuf = ByteBuffer.allocate(1024);
	//3.
	
	
	public Server(int port){
		try {
			//1.打开路复用器
			this.selector = Selector.open();
			//2.打开服务器通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//3.设置服务器通道为非阻塞模式
			ssc.configureBlocking(false);
			//4.绑定地址
			ssc.bind(new InetSocketAddress(port));
			//5.把服务器通道注册到路复用器上，并监听阻塞事件
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("Server start, port:"+port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				//1.必须要让多路复用器开始监听
				this.selector.select();
				//2.返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
				//3.进行遍历
				while(keys.hasNext()){
					//4.获取一个选择的元素
					SelectionKey key = keys.next();
					//5.直接从容器中移除一个
					keys.remove();
					//6.如果有效的
					if(key.isValid()){
						//7.如果为阻塞状态
						if(key.isAcceptable()){
							this.accept(key);
						}
						//8.如果为可读状态
						if(key.isReadable()){
							this.read(key);
						}
						//9.如果为课写状态
						if(key.isWritable()){
							
						}
						//10.如果为连接状态
						if(key.isConnectable()){
							
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//可读状态
	private void read(SelectionKey key){
		try {
			//1.清空缓冲区旧的数据
			this.readBuf.clear();
			//2.获取之前的注册的socket通道对象
			SocketChannel sc = (SocketChannel) key.channel();
			//3.读取数据
			int count = sc.read(this.readBuf);
			//4.如果没有数据
			if(count == -1){
				key.channel().close();
				key.cancel();
				return;
			}
			//5.有数据则进行读取  读取之前需要进行复位方法（把position和limit进行复位）
			this.readBuf.flip();
			//6.根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
			byte[] bytes = new byte[this.readBuf.remaining()];
			//7.接收缓冲区数据
			this.readBuf.get(bytes);
			//8.打印结果
			String body = new String(bytes).trim();
			System.out.println("Server : " + body);
			//9.写给客户端的数据
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//阻塞状态  (与客户端建立连接)
	private void accept(SelectionKey key){
		try {
			//1.获取服务通道
			ServerSocketChannel ssc =(ServerSocketChannel) key.channel();
			//2.执行阻塞方法
			SocketChannel sc = ssc.accept();
			//3.设置为阻塞模式
			sc.configureBlocking(false);
			//4.注册到多路复用器上，并设置读取标识
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Server(8765)).start();
	}
}
