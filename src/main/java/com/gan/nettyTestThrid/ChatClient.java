package com.gan.nettyTestThrid;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:46
 */
public class ChatClient {
    public static void main(String[] args) throws  Exception{
        //事件循环组,只有一个循环组
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            /**
             * handler()里面一定要填对，上次就是因为看错了，写成了Server的Initizlizer，一定用客户端的
             */
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new ChatClientInitizlizer());
            /**
             * 这里和之前例子不一样，他获得的是一个Channel
             * 表示和服务器端连接
             */
            Channel channel =bootstrap.connect("localhost",8899).sync().channel();

            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            for(; ;){
                //读取一行数据，回车即读取
                channel.writeAndFlush(br.readLine()+"\r\n");
            }
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
