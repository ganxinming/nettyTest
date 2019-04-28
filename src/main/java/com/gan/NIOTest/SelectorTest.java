package com.gan.NIOTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author Badribbit
 * @create 2019/4/11 16:17
 */
public class SelectorTest {
    public static void main(String[] args) throws IOException {
        /**
         * 查看源码selector不能被new出来，他是抽象类。实现了Closeable，会自动close方法。
         * 一般都采用open方法。
         * 解释：Selector(选择器)是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
         * 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
         */
        int [] ports=new int[5];
        ports[0]=5000;
        ports[1]=5001;
        ports[2]=5002;
        ports[3]=5003;
        ports[4]=5004;
        //一般创建selector的方法
        Selector selector=Selector.open();

        System.out.println("selector的底层创建对象："+SelectorProvider.provider().getClass());
        //for循环用来将多个端口地址和通道绑定
        for (int i=0;i<5;i++){
            //打开ServerSocketChannel通道
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            /**配置成不阻塞必须写
            * 与Selector一起使用时，Channel必须处于非阻塞模式下。
             * 这意味着Channel与Selector不能一起使用。
             *
             */
            serverSocketChannel.configureBlocking(false);
            //通过ServerSocketChannel的socket（）方法获得serverSocket对象。
            ServerSocket serverSocket=serverSocketChannel.socket();
            //将每一个serverSocket和端口号绑定
            InetSocketAddress address=new InetSocketAddress(ports[i]);
            serverSocket.bind(address);
            /**
             * 将channel注册到selector上，只对感性的事件监听
             * 注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
             * 这里对accept感兴趣
             * 可以配置对多个事件感兴趣,具体方法百度
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            /**
             * 通道触发了一个事件意思是该事件已经就绪。四种事件，用四种key表示。
             * 所以，某个channel成功连接到另一个服务器称为“连接就绪”。 SelectionKey.OP_CONNECT
             * 一个server socket channel准备好接收新进入的连接称为“接收就绪”。  SelectionKey.OP_ACCEPT
             * 一个有数据可读的通道可以说是“读就绪”。SelectionKey.OP_READ
             * 等待写数据的通道可以说是“写就绪”。  SelectionKey.OP_WRITE
             */
            System.out.println("监听端口为："+ports[i]);
        }
        /**
         * 一个Selector对象会包含3种类型的SelectionKey集合：
         *
         * all-keys集合 —— 当前所有向Selector注册的SelectionKey的集合，Selector的keys()方法返回该集合
         * selected-keys集合 —— 相关事件已经被Selector捕获的SelectionKey的集合，Selector的selectedKeys()方法返回该集合
         * cancelled-keys集合 —— 已经被取消的SelectionKey的集合，Selector没有提供访问这种集合的方法
         * 当register()方法执行时，新建一个SelectioKey，并把它加入Selector的all-keys集合中。
         *
         * 如果关闭了与SelectionKey对象关联的Channel对象，或者调用了SelectionKey对象的cancel方法，
         * 这个SelectionKey对象就会被加入到cancelled-keys集合中，表示这个SelectionKey对象已经被取消。
         *
         * 在执行Selector的select()方法时，如果与SelectionKey相关的事件发生了，
         * 这个SelectionKey就被加入到selected-keys集合中，程序直接调用selected-keys集合的remove()方法，
         * 或者调用它的iterator的remove()方法，都可以从selected-keys集合中删除一个SelectionKey对象。
         */
        while(true){
            /**
             * 在执行Selector的select()方法时，如果与SelectionKey相关的事件发生了，
             * 这个SelectionKey就被加入到selected-keys集合中
             */
            /**
             * 注意：通过测试发现这个在进入循环后，这个selector.select()，会检查是否有selectkey
             * 如果没有，代码根本不往下走
             * 通过打断点调试
             * 相当于不断查询是否有关注的事件出现
             */
            int keyNumbers=selector.select();
            System.out.println("测试下");
            System.out.println("返回key的数量："+keyNumbers);
            //获得所有key，因为同一时间可能连接多个channel，产生多个key
            Set<SelectionKey>  selectionKeys=selector.selectedKeys();
            System.out.println("因为selector可能监听多个通道，所以返回一个set集合"+selectionKeys);
            //迭代所有已经获得的Selectedkey
            Iterator<SelectionKey> iterator= selectionKeys.iterator();
            //迭代selectionKeys
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();

                //如果当前key是之前的SelectionKey.OP_ACCEPT这种状态，说明这是个感兴趣的事件。
                if (selectionKey.isAcceptable()){
                    //通过key来获得通道
                    ServerSocketChannel serverSocketChannel= (ServerSocketChannel) selectionKey.channel();
                    //如果客户端连接，获得客户端channel
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //通过selec来监听读事件
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    //如果不移除这个key，他还存在在Selectedkey集合中，那么下次迭代他还存在
                    //所以需要移除
                    iterator.remove();
                    System.out.println("获取客户端连接："+socketChannel);
                }

                 else if (selectionKey.isReadable()){
                    SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
                    int bytesRead=0;
                    while(true){
                        ByteBuffer byteBuffer=ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read= socketChannel.read(byteBuffer);
                        if (read<=0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        bytesRead+=read;
                    }
                    System.out.println("读取："+bytesRead+",来自于"+socketChannel);
                    iterator.remove();

                }

            }
        }

    }
}
