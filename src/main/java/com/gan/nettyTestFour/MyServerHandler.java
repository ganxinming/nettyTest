package com.gan.nettyTestFour;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */

/**
 * 注意这里继承的不是SimpleChannelInboundHandler，而是他的父类ChannelInboundHandlerAdapter
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 触发了某个事件之后就会被调用
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        /**
         * 如果当前事件是IdleStateEvent类型，表示他是个空闲状态
         */
        if (evt instanceof IdleStateEvent){
            //把evt转换成IdleStateEvent类型
            IdleStateEvent event= (IdleStateEvent) evt;
            String eventType=null;
            /**
             * 判断event是什么状态，event.state()返回的是IdleState类型，它是个枚举类型，有三个属性
             * READER_IDLE，读空闲。WRITER_IDLE写空闲，ALL_IDLE什么都不做空闲
             *
             */
            switch (event.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            /**
             * 注意了，这个读空闲和写空闲，指的是当前实现这个Handler的类，即Server
             * 如果Server没有接受到客户端的数据，即为读空闲（因为没有读取数据）
             * 如果Server没有发送消息，即为写空闲
             */
            System.out.println(ctx.channel().remoteAddress()+"---超时事件:"+eventType);
            //如果不关闭，会一直循环判断
            ctx.channel().close();
        }
    }
}
