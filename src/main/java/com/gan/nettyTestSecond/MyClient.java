package com.gan.nettyTestSecond;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:46
 */
public class MyClient {
    public static void main(String[] args) throws  Exception{
        //事件循环组,只有一个循环组
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            /**
             *  注意
             *  1.客户端不在是ServerBootstrap而是Bootstrap
             *  2.反射类不是NioServerSocketChannel而是NioSocketChannel
             *  3.一般使用handler，而不是用childHandler
             *  4.不是bind绑定端口而是connect
             */
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitizlizer());

             ChannelFuture channelFuture =bootstrap.connect("localhost",8899).sync();
             channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
