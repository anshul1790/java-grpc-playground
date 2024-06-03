package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.sec06.requesthandlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;
import sec06.TransferRequest;
import sec06.TransferResponse;
import sec06.TransferServiceGrpc;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}
