package com.gan.protobufTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:46
 */
public class TestClient {
    public static void main(String[] args) throws  Exception{
        //事件循环组,只有一个循环组
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new TestClientInitizlizer());
             ChannelFuture channelFuture =bootstrap.connect("localhost",8899).sync();
             channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
