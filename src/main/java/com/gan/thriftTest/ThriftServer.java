package com.gan.thriftTest;

import com.thrift.generated.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @Author Badribbit
 * @create 2019/4/27 10:10
 */

/**
 * 实现client远程调用Server方法
 * 感悟：这个结果说明了方法还是在Server端。
 * Client调用方法，方法走了一遍，但其实还是在server端走，最后的结果通过网络传输到Client
 * 方法体里的打印值，还是在Server端打印。只有Client端自己打印的值，才会出现在Client中。
 */
public class ThriftServer {
    public static void main(String[] args) throws TTransportException {
        TNonblockingServerSocket socket=new TNonblockingServerSocket(8899);
        //高可用的server,并设置工作线程的最大值和最小值
        THsHaServer.Args arg=new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        //处理器,将实现接口作为泛型，因为客户端那边调用的就是这个，所以后面传输的也是这个对象new PersonServiceImpl()
        PersonService.Processor<PersonServiceImpl> processor=new PersonService.Processor<>(new PersonServiceImpl());
        //设定几个工厂,用来构建需要的信息
        //协议工厂,为什么要这样定义这些工厂，可以上网查
        //协议层:表示数据传输格式，这里TCompactProtocol表示压缩格式,速率很快
        arg.protocolFactory(new TCompactProtocol.Factory());
        //传输层:表示数据的传输方式，这里TFramedTransport是以frame为单位传输,非阻塞式传输
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        //支持的服务模型：THsHaServer半同步，半异步Server
        TServer server=new THsHaServer(arg);
        System.out.println("Thrift Server Started!");
        //一个异步死循环，永远不会退出
        server.serve();
    }
}
