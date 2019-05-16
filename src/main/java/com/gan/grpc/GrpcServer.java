package com.gan.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @Author Badribbit
 * @create 2019/4/28 16:50
 */

/**
 * 实现grpc的server端
 */
public class GrpcServer {
    private Server server;
    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 8899;
        this.server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build()
                .start();
        System.out.println("server started!");
        /**通过关闭程序，发生中断触发钩子函数。
         * 1.Runtime不能直接new，它是单例的，通过静态方法返回。
         * 2.点击stop似乎不能调用钩子，要点击exit才能触发。
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("关闭JVM");
            GrpcServer.this.stop();
        }));

        System.out.println("执行到这里");
    }

    private void stop() {
        if (this.server != null) {
            this.server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    //等待client的连接
    private void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {

            /**
             * 表示只等待三秒钟，到了时间就直接退出不等了。
             * this.server.awaitTermination(3000,TimeUnit.MILLISECONDS);
             */
             this.server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server=new GrpcServer();
        server.start();
        //有了这个代码server就会等待client连接，如果没有立即关闭
        server.blockUntilShutdown();
    }

}
