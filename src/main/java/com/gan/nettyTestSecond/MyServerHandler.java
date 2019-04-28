package com.gan.nettyTestSecond;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */

/**
 * 这里的泛型是String，说明这个传输的是个String对象
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * ChannelHandlerContext ctx: 表示请求上下文信息。可用于获得channel，远程地址等
     * msg ：表示客户端发送来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" : "+msg);
        ctx.channel().writeAndFlush("from Server"+ UUID.randomUUID());

    }
    //异常的捕获，一般出现异常，就把连接关闭
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
