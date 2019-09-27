package com.demo.grpc.client;

import com.demo.grpc.lib.HelloReply;
import com.demo.grpc.lib.HelloRequest;
import com.demo.grpc.lib.SimpleGrpc;
import io.grpc.Channel;

import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;
/**
 * emil:miles02@613.com
 * Created by forezp on 2018/8/11.
 */

@Service
public class GrpcClientService {

    @GrpcClient("demo-grpc-server")
    private Channel serverChannel;

    public String sendMessage(String name) {
        SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(serverChannel);
        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}
