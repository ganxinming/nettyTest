package com.gan.protobufTest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @Author Badribbit
 * @create 2019/4/14 21:47
 */

/**
 * 这里的泛型是String，说明这个传输的是个String对象
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo1.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo1.MyMessage msg) throws Exception {

    }
    //在channelActive开始后，发送消息给Server
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random=new Random().nextInt(3);
        MyDataInfo1.MyMessage myMessage=null;
        //通过产生一个随机数，来确定使用哪种随机数
        /**
         * 其实大家可能觉得这样的if else很麻烦，跟之前的学的springmvc啊之类的框架完全不一样，
         * 比如：Reuqestmapping，直接路径映射，就能找到对应的Controller方法。
         * 但是别忘了，它DispatcherServlet，他帮助springmvc做了if else 判断，才能正确
         * 的找到是哪个方法。
         * netty因为是底层框架，所以没有跟他们一样，这些if else 需要自己写.
         */
        if (0 == random){
            //使用枚举类型来构造一个对象
            myMessage=MyDataInfo1.MyMessage.newBuilder().
                    setDataType(MyDataInfo1.MyMessage.DataType.PersonType).
                    setPerson(MyDataInfo1.Person.newBuilder().
                            setName("张三").setId(11).
                            setAddress("北京").build())
                    .build();
        }
        else if (1 == random) {
            myMessage=MyDataInfo1.MyMessage.newBuilder().
                    setDataType(MyDataInfo1.MyMessage.DataType.DogType).
                    setDog(MyDataInfo1.Dog.newBuilder().
                            setName("小狗狗").setAge(8).build())
                    .build();
        }else{
            myMessage=MyDataInfo1.MyMessage.newBuilder().
                    setDataType(MyDataInfo1.MyMessage.DataType.CatType).
                    setCat(MyDataInfo1.Cat.newBuilder().
                            setName("小猫咪").setAge(7).build())
                    .build();
        }

    /** 使用这种单一类型太耦合了，这也是RPC的通病，传输的数据类型必须是双方已知确定的。
     * 这才会导致这种只能传递单一类型。这里，使用枚举类型很好的解决这个问题。
        MyDataInfo.Student student=MyDataInfo.Student.newBuilder().
                setName("王五").setId(22).setAddress("永乐").build();
     */
        ctx.channel().writeAndFlush(myMessage);
    }
}
