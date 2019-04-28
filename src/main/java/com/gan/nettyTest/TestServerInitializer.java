package com.gan.nettyTest;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author Badribbit
 * @create 2019/4/14 11:11
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    //这是一个回调的方法，在channel被注册时被调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道，里面有很多ChannelHandler，这些就像拦截器，可以做很多事
        ChannelPipeline pipeline=ch.pipeline();
        //增加一个处理器，neet提供的.名字默认会给，但还是自己写一个比较好
        /**
         * 注意这些new的对象都是多例的，每次new出来新的对象,因为每个连接的都是不同的用户
         */
        //HttpServerCodec完成http编解码，可查源码
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //增加一个自己定义的处理器hander
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
