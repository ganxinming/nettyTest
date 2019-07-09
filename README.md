## netty基础练习导航
## :heart_eyes:各文件说明（以下按照学习顺序排列）
## :blush: - [NIOTest](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/NIOTest) /文件夹下的知识点和技术
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
## :sleepy: - [nettyTest](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/nettyTest)/文件夹下的知识点和技术
使用netty技术完成服务端，步骤如下：
1.创建两个事件循环组。bossGroup，workerGroup
2.创建ServerBootstrap服务端，注入三个参数EventLoopGroup，NioServerSocketChannel.class，childHandler.(看代码)
3.绑定一个端口，进行监听。bind(8899).sync()
4.关闭监听，关闭事件循环组。
## :grin: - [nettyTestSecond](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/nettyTestSecond)/文件夹下的知识点和技术
服务端和客户端代码差不多(详见码)  
完成客户端和服务端通信主要三个类：   
1.Server/Client  : 开启服务端/客户端，完成监听  
2.Initizlizer ：用于注入到Bootstrap类中，类似于拦截器，可以做些过滤的功能。  
3.Handler     : 用于注入到Initizlizer类中，一般自定义的Handler完成消息接受和发送。  
## :stuck_out_tongue_closed_eyes: - [nettyTestThrid](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/nettyTestThrid)/文件夹下的知识点和技术
1.多个客户端连接服务端,可以相互发送消息，并且有上下线通知.
## :stuck_out_tongue_winking_eye:  - [nettyTestFour](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/nettyTestFour)/文件夹下的知识点和技术
测试心跳机制：心跳机制：判断是否还处于连接状态。
如我们的客户端是移动手机并且已经建立好了连接，当打开飞行模式（或者强制关机）的时候，我们就无法感知当前连接已经断开了（handlerRemoved不会触发的）
## :open_mouth: - [nettyTestFive](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/nettyTestFive)/文件夹下的知识点和技术
客户端发送给服务端，服务端接收并能响应给客户端.客户端通过前端页面text.html来进行连接
## :confused:  - [protobufTest](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/protobufTest)/文件夹下的知识点和技术
利用protobuf，在netty中传输.
## :sob: - [thriftTest](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/thriftTest)/文件夹下的知识点和技术
使用thrift，完成服务端和和客户端。
## :yum: - [grpc](https://github.com/ganxinming/nettyTest/tree/master/src/main/java/com/gan/grpc)/文件夹下的知识点和技术
使用grpc，完成服务端和和客户端。
