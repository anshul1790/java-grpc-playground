package com.learn.javagrpcplayground.sec06.client;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import sec06.BalanceCheckRequest;
import sec06.BankServiceGrpc;

@Slf4j
public class GrpcClient {

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        var stub = BankServiceGrpc.newBlockingStub(channel);
        var balance = stub.getAccountBalance(BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build());
        log.info("{}", balance);
    }
}
