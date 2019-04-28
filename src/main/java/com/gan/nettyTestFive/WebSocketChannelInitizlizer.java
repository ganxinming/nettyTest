package com.gan.nettyTestFive;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */
public class WebSocketChannelInitizlizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 因为这是基于http协议的，所以使用http编解码器。前面已经讲过了
         */
        pipeline.addLast(new HttpServerCodec());
        //以块的方式去写
        pipeline.addLast(new ChunkedWriteHandler());
        /**
         *  特别重要，http数据在传输过程是分段的
         *  HttpObjectAggregator,而他就是将多个段聚合起来。
         */
        pipeline.addLast(new HttpObjectAggregator(8192));
        /**
         * 对于webSocket，他的数据传输是以frame（帧）的形式传递
         * 可以查看WebSocketFrame，他有六个子类
         * /ws,表示的websocket的地址。
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFranmeHandler());

    }

}
