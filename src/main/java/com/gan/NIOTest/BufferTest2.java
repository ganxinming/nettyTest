package com.gan.NIOTest;

import java.nio.ByteBuffer;

/**
 * @Author Badribbit
 * @create 2019/4/8 11:22
 */
public class BufferTest2 {
    /**
     * 只读buffer，顾名思义只能用来读，很有用的，让别人不能修改，只能读
     * 只读buffer底层是：HeapByteBufferR，而普通buffer底层是：HeapByteBuffer
     * 如果向里面put数据会抛出异常，看源码会发现，他的put方法就是直接抛异常。
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i=0;i<10;i++){
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();

        ByteBuffer onlyReadBuffer=byteBuffer.asReadOnlyBuffer();
        while (onlyReadBuffer.hasRemaining()){
            System.out.println(onlyReadBuffer.get());
        }
        //onlyReadBuffer.put((byte) 10);
    }
}
