package com.gan.NIOTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Badribbit
 * @create 2019/4/8 10:33
 *
 */
public class readAndWrite1 {
    /**
     * allocateDirect返回的是一个DirectByteBuffer
     * 那么DirectByteBuffer相比于HeapByteBuffer有什么不同
     * 1.HeapByteBuffer这个对象是存在堆上。而DirectByteBuffer，在使用堆内存的同时还会使用堆外内存，他这个对象在堆上，而buffer数组在堆外内存。
     * 2.堆外内存：即调用native方法，使用C或C++编写的方法处理数据。（在堆外，但是我们java是在堆内存啊，要使用堆外内存怎么办）
     * 3.在DirectByteBuffer中有个long类型的address指向堆外内存，那么凭借这个我们就可以连接两块内存。
     * 那为啥要用到堆外内存？
     * 1.普通的在堆上分配的内存，在操作系统进行读取时，需要先将堆内存复制一份到操作系统，这样速度就受限了。(这种复制情况也是受限于需要对IO设备进行交互，例如文件读取等)
     * (疑问：为啥操作系统需要复制一份数据，不可以直接读取吗？可以，但是不能这样。主要原因是java有垃圾回收机制，操作系统可能就在读取数据时发生了垃圾回收
     * 其中标记整理法会将内存压缩，这样不就打乱了数组顺序吗？所以操作系统复制一份进行操作.
     * 因此DirectByteBuffer在堆外分配，就不需要操作系统拷贝了，也被称为0拷贝.
     * )
     * 2.如果使用堆外内存，操作系统直接读取，速度超快。
     * 总结：DirectByteBuffer（直接缓冲区），直接本地IO操作，避免了java虚拟机复制一块中间缓冲区。
     * * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileInputStream in=new FileInputStream("input.txt");
        FileOutputStream out=new FileOutputStream("out.txt");
        FileChannel fileInChannel=in.getChannel();
        FileChannel fileOutChannel=out.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(512);
        while (true){
            byteBuffer.clear();
            int read=fileInChannel.read(byteBuffer);
            System.out.println("read:"+read);
            if ( -1 == read){
                break;
            }
            byteBuffer.flip();
            fileOutChannel.write(byteBuffer);

        }
        fileInChannel.close();
        fileOutChannel.close();
    }
}
