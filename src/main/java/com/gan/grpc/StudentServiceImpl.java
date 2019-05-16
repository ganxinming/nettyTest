package com.gan.grpc;

import com.protobuf.proto.*;
import io.grpc.stub.StreamObserver;

/**
 * @Author Badribbit
 * @create 2019/4/28 16:32
 */

/**
 * 使用grpc实现
 * 实现service定义的方法，只需通过下面这种继承抽象类的方式实现。
 * 注意下面实现的方法都是写个简单例子，并不是完成相应方法的功能
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     *注意：这里发现他的返回值是void，可是我们定义service时，返回值是MyResponse啊
     * grpc跟Thrift不一样，他是通过StreamObserver来返回对象，即第二个参数
     * @param request 传入的参数对象
     * @param responseObserver 用于返回结果的对象
     */
    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收客户端信息："+request.getUsername());
        //开始返回对象（即返回值），对象的构建和protobuf一样
        responseObserver.onNext(MyResponse.newBuilder().setRealName("张三").build());
        //标志结束
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentByAge(MyAge request, StreamObserver<MyStudent> responseObserver) {
        System.out.println("接受到的客户端age："+request.getAge());
        //跟上个方法不一样，这是构造一个stream对象（即多个对象相当于集合）
        responseObserver.onNext(MyStudent.newBuilder().setName("Linux").setAge(20).build());
        responseObserver.onNext(MyStudent.newBuilder().setName("Centos").setAge(18).build());
        responseObserver.onNext(MyStudent.newBuilder().setName("ubuntu").setAge(16).build());
        responseObserver.onCompleted();;
    }
}
