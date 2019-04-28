package com.gan.protobufTest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */

/**
 * 这里的泛型是MyDataInfo.Student，说明这个传输的是个MyDataInfo.Student对象
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo1.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo1.MyMessage msg) throws Exception {
        /**  //打印接受到的protobuf
         System.out.println(msg.getName());
         System.out.println(msg.getId());
         System.out.println(msg.getAddress());
         */
        MyDataInfo1.MyMessage.DataType dataType = msg.getDataType();

        if (dataType == MyDataInfo1.MyMessage.DataType.PersonType) {
            MyDataInfo1.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getId());
            System.out.println(person.getAddress());
        } else if (dataType == MyDataInfo1.MyMessage.DataType.DogType) {
            MyDataInfo1.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        } else {
            MyDataInfo1.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getAge());
        }

    }
}
