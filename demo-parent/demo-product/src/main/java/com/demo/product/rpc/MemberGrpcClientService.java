package com.demo.product.rpc;

import org.springframework.stereotype.Service;


/**
 * @author zzj
 * @create 2018-11-19 16:02
 **/

@Service
public class MemberGrpcClientService {

//    @GrpcClient("demo-member")
//    private Channel serverChannel;
//    public  String sendMessage(String name){
//        GreeterGrpc.GreeterBlockingStub stub=GreeterGrpc.newBlockingStub(serverChannel);
//        GreeterOuterClass.HelloReply response=stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName(name).build());
//        return  response.getMessage();
//    }
}
