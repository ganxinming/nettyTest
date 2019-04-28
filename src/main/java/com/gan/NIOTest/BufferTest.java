package com.gan.NIOTest;

import java.nio.ByteBuffer;

/**
 * @Author Badribbit
 * @create 2019/4/8 11:22
 */
public class BufferTest {
    /**
     * byteBuffer可以存不同的类型，但是取的时候得按顺序取（字节码会自动识别）
     * 但如果循环取不同类型，会取错值。
     * 像intBuffer，只能存int类型。但他底层最后还是用byteBuffer
     * 总结，byteBuffer真牛逼
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(64);
        byteBuffer.putInt(123);
        byteBuffer.putLong(123213123L);
        byteBuffer.putChar('我');
        byteBuffer.putShort((short)2);

        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }

    }
}
