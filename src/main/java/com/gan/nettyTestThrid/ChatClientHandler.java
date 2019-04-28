package com.gan.nettyTestThrid;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */

/**
 * 这里的泛型是String，说明这个传输的是个String对象
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx 上下文请求对象
     * @param msg 表示服务端发来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务端发来的："+msg);
        System.out.println("kkkkk");
    }
}
