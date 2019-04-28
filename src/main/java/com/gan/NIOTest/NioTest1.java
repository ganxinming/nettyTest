package com.gan.NIOTest;

import java.nio.IntBuffer;
import java.util.Random;

public class NioTest1 {
    public static void main(String[] args) {
        //指定buffer长度
        IntBuffer buffer=IntBuffer.allocate(10);
        //加入随机数
        for (int i=0;i<buffer.capacity();i++){
            int random=new Random().nextInt(20);
            buffer.put(random);
        }
        //将buffer转换一下，读写切换
        buffer.flip();
        //读取
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());

        }
    }
}
