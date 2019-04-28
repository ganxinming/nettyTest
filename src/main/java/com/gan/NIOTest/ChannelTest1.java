package com.gan.NIOTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest1 {
    public static void main(String[] args) throws IOException {
        FileOutputStream out=new FileOutputStream("2.txt");
        //通过inputstream获得输入流
        FileChannel fileChannel=out.getChannel();
        //设置buffer的长度
        ByteBuffer byteBuffer=ByteBuffer.allocate(215);

        byte[] message="selector channel bytebuffer".getBytes();

        for (int i=0;i<message.length;i++){
            byteBuffer.put(message[i]);
        }
        byteBuffer.flip();
        //循环读出
        fileChannel.write(byteBuffer);
        out.close();
    }
}
