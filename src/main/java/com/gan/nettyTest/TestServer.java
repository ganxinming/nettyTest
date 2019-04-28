package com.gan.nettyTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author Badribbit
 * @create 2019/4/13 20:38
 */
public class TestServer {
    public static void main(String[] args) throws  Exception{
        /**
         * 事件循环组，就是死循环
         */
        //仅仅接受连接，转给workerGroup，自己不做处理
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        //真正处理
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            //很轻松的启动服务端代码
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            //childHandler子处理器,传入一个初始化器参数TestServerInitializer（这里是自定义）
            //TestServerInitializer在channel被注册时，就会创建调用
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new TestServerInitializer());
            //绑定一个端口并且同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture=serverBootstrap.bind(8899).sync();
            //对关闭的监听
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            //循环组优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
