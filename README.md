## netty基础练习导航
## :heart_eyes:各文件说明（以下按照学习顺序排列）
## :blush: NIOTest/文件夹下的知识点和技术
1.byteBuffer可以存不同的类型，但是取的时候得按顺序取（字节码会自动识别），但如果循环取，会取错值。具体API看代码。  
2.byteBuffer.slice(),相当于做一个buffer的快照,slice前，设置positon和limit，那么复制一个(positon,limit)的快照.  
3.只读buffer底层是：HeapByteBufferR，而普通buffer底层是：HeapByteBuffer.  
4.使用channel进行读或写。(双向，读写之间需要flip，进行转换)  
5.MappedByteBuffer作用：可以让文件直接在内存（堆外的内存）中进行修改，而操作系统不需要拷贝一次，有点像DirectByteBuffer  
6.使用NIO模拟客户端和服务端通信。  
7.完成文件的读写复制。  
8.Scattering:在将数据写入到buffer中时，可以采用buffer数组，依次写入，一个buffer满了就写下一个。    
Gatering：在将数据读出到buffer中时，可以采用buffer数组，依次读入，一个buffer满了就读下一个。  
9.Selector(选择器)是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个channel，从而管理多个网络连接
## :sleepy: nettyTest/文件夹下的知识点和技术
