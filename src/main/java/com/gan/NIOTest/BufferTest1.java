package com.gan.NIOTest;

import java.nio.ByteBuffer;

/**
 * @Author Badribbit
 * @create 2019/4/8 11:22
 */
public class BufferTest1 {
    /**
     * byteBuffer.slice(),相当于做一个buffer的快照
     * 特点：1.slice前，设置positon和limit，那么复制一个[positon,limit)的快照
     * 2.在这个快照上修改值，会实际改变buffer的值。
     * 3.但是slicebuffer的位置不会影响buffer
     * @param args
     */

    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        for (int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte) i);
        }
        //设置postition和limit位置
        byteBuffer.position(2);
        byteBuffer.limit(6);

        ByteBuffer sliceBufeer=byteBuffer.slice();
        byteBuffer.clear();
        for (int i=0;i<sliceBufeer.capacity();i++){
            sliceBufeer.put(i, (byte) (sliceBufeer.get(i)*2));
        }
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }
}
