package com.gan.nettyTestFour;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:46
 */

/**
 * 心跳机制：判断是否还处于连接状态。
 * 提问：1.比如说之前的多客户端通信demo，当客户端断开与服务器连接的时候会触发handlerRemoved方法，
 * 那么我们就知道该服务的状态了。为什么还需要心跳包去感知呢？
 * 真实情况远比我们想象中的复杂，比如我们的客户端是移动手机并且已经建立好了连接，当打开飞行模式（或者强制关机）的时候
 * 我们就无法感知当前连接已经断开了（handlerRemoved不会触发的），
 */

/**
 * 测试：这里就写client了，可以直接用Third包下的Client。
 */
public class MyServer {
    public static void main(String[] args) throws  Exception{
        //事件循环组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            /**
             * handler()用于bossGroup
             * childHandler()用于workerGroup
             */
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.INFO)).
                    childHandler(new MyServerInitizlizer());
            ChannelFuture channelFuture=serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
