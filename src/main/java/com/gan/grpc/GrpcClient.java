package com.gan.grpc;

import com.protobuf.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @Author Badribbit
 * @create 2019/4/28 17:19
 */

/**
 * grpc实现客户端client
 * 这段代码是根据下载的代码示例中更改的
 */
public class GrpcClient {

    private final ManagedChannel channel;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    GrpcClient(ManagedChannel channel) {
        this.channel = channel;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("localhost", 8899);
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub=StudentServiceGrpc.newBlockingStub(client.channel);
        MyResponse myResponse=blockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("李四").build());
        System.out.println(myResponse.getRealName());
        System.out.println("-------------------");
        //其实返回的流对象就是个迭代器对象，通过迭代器取
        Iterator<MyStudent> iterator=blockingStub.getStudentByAge(MyAge.newBuilder().setAge(20).build());
        //
        while(iterator.hasNext()){
            MyStudent student=iterator.next();
            System.out.println(student.getName()+":"+student.getAge());
        }
        client.shutdown();
    }

}


