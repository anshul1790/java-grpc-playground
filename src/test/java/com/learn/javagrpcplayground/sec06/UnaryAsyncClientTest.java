package com.learn.javagrpcplayground.sec06;

import io.grpc.stub.AbstractAsyncStub;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec06.AccountBalance;
import sec06.BalanceCheckRequest;

import java.util.concurrent.CountDownLatch;

public class UnaryAsyncClientTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(UnaryAsyncClientTest.class);

    @Test
    public void getBalanceTest() throws InterruptedException {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        var latch = new CountDownLatch(1);
        this.asyncStub.getAccountBalance(request, new StreamObserver<>() {
            @Override
            public void onNext(AccountBalance value) {
                log.info("Async balance received: {}", value);
                Assertions.assertEquals(100, value.getBalance());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

        latch.await();
    }

}
