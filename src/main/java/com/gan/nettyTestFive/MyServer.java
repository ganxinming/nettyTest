package com.gan.nettyTestFive;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:46
 */

/**
 * 基于webSocket的长连接的全双工的交互（真是强大，改变了以往多次请求的约束，现在是长连接了，而且支持全双工通信）
 * 客户端发送给服务端，服务端接收并能响应给客户端.客户端通过前端页面text.html来连接
 * 1.基于webSocket，ws协议.会出现一个101状态码，表示由http协议转成ws协议（协议升级）
 * 2.每次刷新后，新建一个连接，而不是以前那个连接
 * 3.不要以为连接就一定能关闭，在断网或者断电情况，是检测不出来是否断连接的。（所以前面要有心跳机制）
 *
 */
public class MyServer {
    public static void main(String[] args) throws  Exception{
        //事件循环组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.INFO)).
                    childHandler(new WebSocketChannelInitizlizer());
            /**
             * 这里的端口绑定，和之前的端口直接绑定是一样的
             */
            ChannelFuture channelFuture=serverBootstrap.bind(new InetSocketAddress(8899)).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
