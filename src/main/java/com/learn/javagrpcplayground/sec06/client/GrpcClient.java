package com.learn.javagrpcplayground.sec06.client;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import sec06.AccountBalance;
import sec06.BalanceCheckRequest;
import sec06.BankServiceGrpc;

@Slf4j
public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        /*var stub = BankServiceGrpc.newBlockingStub(channel);
        var balance = stub.getAccountBalance(BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build());
        log.info("{}", balance);*/


        var stub = BankServiceGrpc.newStub(channel);
        stub.getAccountBalance(BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build(), new StreamObserver<>() {
            @Override
            public void onNext(AccountBalance value) {
                log.info("Balance is {}", value.getBalance());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                log.info("completed");
            }
        });

        log.info("Done");

        Thread.sleep(1000);

    }
}
