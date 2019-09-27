package com.demo.product.grpc.service;

import com.demo.grpc.lib.HelloReply;
import com.demo.grpc.lib.HelloRequest;
import com.demo.grpc.lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

/**
 * @author zzj
 * @create 2018-11-21 14:28
 **/
@GrpcService(SimpleGrpc.class)
public class GrpcServerService  extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello =============> " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
