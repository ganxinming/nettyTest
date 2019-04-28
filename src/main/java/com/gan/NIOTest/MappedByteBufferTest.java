package com.gan.NIOTest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Badribbit
 * @create 2019/4/11 14:41
 */

/**
 * MappedByteBuffer作用：可以让文件直接在内存（堆外的内存）中进行修改，而操作系统不需要拷贝一次，有点像DirectByteBuffer
 * 实际上DirectByteBuffer也是MappedByteBuffer作用的子类。
 *
 * 注意：执行完代码后，IDEA的文件并没有及时改变，但是如果我们在外面打开文件，他的确是发生改变的。
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccess = new RandomAccessFile("file1.txt", "rw");
        FileChannel fileChannel = randomAccess.getChannel();
        //0-5范围内容可以直接在内存操作
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'b');
        mappedByteBuffer.put(1, (byte) 'b');
        randomAccess.close();
    }
}

