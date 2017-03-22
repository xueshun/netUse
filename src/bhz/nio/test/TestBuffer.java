package bhz.nio.test;

import java.nio.IntBuffer;

/**
 * nio  非阻塞的IO流
 * @author Administrator
 *		NIO  和   BIO 的区别
 *		1.Channels and buffers (通道和缓冲区)：
 *			标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，
 *			数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中 
 *		2.Asy
 */			
public class TestBuffer {
	public static void main(String[] args) {
		//1 基本操作
		
		//创建指定长度的缓冲区
		/*IntBuffer buf = IntBuffer.allocate(10);
		buf.put(13);
		buf.put(21);
		buf.put(35);
		
		//把位置复位为0，也就是position位置 3->0
		buf.flip();
		System.out.println("使用flip复位："+ buf);
		System.out.println("容量为："  + buf.capacity());
		//容量一旦初始化后就不允许修改
		System.out.println("限制为" + buf.limit());
		
		
		System.out.println("获取下标为1的元素：" + buf.get(1));
		System.out.println("get(index)方法，position位置不会改变："+buf);
		buf.put(1,4);
		System.out.println("put(index, change)方法，position位置不会改变：" + buf);
		
		for (int i = 0; i <buf.limit(); i++) {
			//调用get方法会使其缓冲区位置（position）向后递增一位
			System.out.println(buf.get()+"\t");
		}
		System.out.println("buf对象遍历之后为：" + buf);*/
		
		
		//2  wrap方法使用
		/**
		 * wrap方法会包裹一个数组：  一般这种用法不会先初始化缓存对象的长度，因为没有意义，最后还会被wrap所包裹的数组覆盖掉。 
		 * 	并且wrap方法修改缓冲区对象的时候，数组本身也会跟着发生变化。	
		 */
		/*int[] arr = new int[] {1,2,5};
		IntBuffer buf1 = IntBuffer.wrap(arr);  
		
		System.out.println(buf1);

		for (int i = 0; i <buf1.limit(); i++) {
			System.out.println(buf1.get()+"\t");
		}
		//这样使用表示容量为数组的长度，但是可操作的元素只有时间键入缓存区的元素长度
		IntBuffer buf2 = IntBuffer.wrap(arr,0,2);
		
		System.out.println(buf2);
		
		for (int i = 0; i <buf2.limit(); i++) {
			System.out.println(buf2.get()+"\t");
		}*/
		
		
		//3.其他方法
		IntBuffer buf1 = IntBuffer.allocate(10);
		int[] arr = new int[]{1,2,5};
		buf1.put(arr);
		System.out.println(buf1);
		
		//一种复制的方法
		IntBuffer buf2 = buf1.duplicate();
		System.out.println(buf2);
		
		//设置buf1的位置长度
		
		//buf1.position(0);
		buf1.flip();
		System.out.println(buf1);
		
		System.out.println("可读数据："+buf1.remaining());
		System.out.println(buf1);
		/*for (int i = 0; i <buf1.limit(); i++) {
			System.out.println(buf1.get());
		}*/
		
		//将缓冲区数据放入arr2数组中
		int[] arr2 = new int[buf1.remaining()];
		//buf1.get(arr2);
		for (int i : arr2) {
			System.out.println(Integer.toBinaryString(i)+",");
		}
		
		
		
	}
}
