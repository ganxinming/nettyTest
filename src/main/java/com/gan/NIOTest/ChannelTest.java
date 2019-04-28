package com.gan.NIOTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {
    /**
     * 无论是input还是output都可以直接getChannel。
     * 从侧面也说明管道是双向的。
     * channel.read(buffer).可以认为是buffer从channel读
     * channel.write(buffer).可以认为是buffer写入channel
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileInputStream in=new FileInputStream("1.txt");
        //通过inputstream获得输入流
        FileChannel fileChannel=in.getChannel();
        //设置buffer的长度，即底层数组长度
        ByteBuffer byteBuffer=ByteBuffer.allocate(215);
        //将数据读入buffer
        fileChannel.read(byteBuffer);

        byteBuffer.flip();
        //循环读出
        byte[] bytes=new byte[215];
        int i=0;
        while (byteBuffer.remaining()>0){
            bytes[i]=byteBuffer.get();
            i++;
        }
        System.out.println(new String(bytes));
        System.out.println("########");
        in=new FileInputStream("2.txt");
        fileChannel=in.getChannel();
       // byteBuffer.clear();
        byteBuffer.flip();
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        //循环读出
         bytes=new byte[215];
         i=0;
        while (byteBuffer.remaining()>0){
            bytes[i]=byteBuffer.get();
            i++;
        }
        System.out.println(new String(bytes));
     /*    bytes=new byte[215];
        i=0;
        while (byteBuffer.remaining()>0){
            bytes[i]=byteBuffer.get();
            i++;
        }
        System.out.println(new String(bytes));*/
        in.close();

    }
}
