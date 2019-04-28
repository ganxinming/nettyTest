package com.gan.NIOTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author Badribbit
 * @create 2019/4/11 15:13
 */

/**
 * Scattering:在将数据写入到buffer中时，可以采用buffer数组，依次写入，一个buffer满了就写下一个。
 * Gatering：在将数据读出到buffer中时，可以采用buffer数组，依次读入，一个buffer满了就读下一个。
 */

/**
 * 使用方式：打开cmd telnet locakhost 8899
 * 连接后可以输入字符串了
 */
public class ScatteringAndGatherIng {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        InetSocketAddress address=new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength=2+3+5;

        ByteBuffer[] byteBuffers=new ByteBuffer[3];
        byteBuffers[0]=ByteBuffer.allocate(2);
        byteBuffers[1]=ByteBuffer.allocate(3);
        byteBuffers[2]=ByteBuffer.allocate(5);

        SocketChannel socketChannel=serverSocketChannel.accept();
        while (true){
            int byteRead=0;
            //接受客户端写入的的字符串
            while(byteRead<messageLength){
                long r=socketChannel.read(byteBuffers);
                byteRead+=r;
                System.out.println("byteRead:"+byteRead);
                //通过流打印
                Arrays.asList(byteBuffers).stream().
                        map(buffer -> "postiton:"+ buffer.position() +",limit:"+buffer.limit()).
                        forEach(System.out::println);

            }
            //将所有buffer都flip。
            Arrays.asList(byteBuffers).
                    forEach(buffer -> {
                        buffer.flip();
                    });
            //将数据读出回显到客户端
            long byteWrite=0;
            while (byteWrite < messageLength) {
                long r=socketChannel.write(byteBuffers);
                byteWrite+=r;
            }
            //将所有buffer都clear
            Arrays.asList(byteBuffers).
                    forEach(buffer -> {
                        buffer.clear();
                    });

            System.out.println("byteRead:"+byteRead+",byteWrite:"+byteWrite+",messageLength:"+messageLength);
        }
    }
}
