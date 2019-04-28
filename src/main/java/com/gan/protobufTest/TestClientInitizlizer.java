package com.gan.protobufTest;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */
public class TestClientInitizlizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        /**
         * 主要是这四个ProtobufHanderl，用于protobuf编解码。
         */
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        /**这个handler，需要传入一个参数MessageLite，即我们需要传输数据的那个实例,耦合太强了
         * 且不能使用多种数据，可以使用下面的枚举类型,解决这个问题.
        *  pipeline.addLast(new ProtobufDecoder(MyDataInfo.Student.getDefaultInstance()));
         * */
        pipeline.addLast(new ProtobufDecoder(MyDataInfo1.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        //因为这里我们是针对MyDataInfo.Student数据，所以下面自定义的handler中，咱们的泛型也是MyDataInfo.Student类型
        pipeline.addLast(new TestClientHandler());
    }
}
