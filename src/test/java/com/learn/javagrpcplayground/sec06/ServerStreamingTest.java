package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec06.Money;
import sec06.WithdrawRequest;

public class ServerStreamingTest extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(ServerStreamingTest.class);

    @Test
    public void blockingClientWithdrawTest() {
        var request = WithdrawRequest.newBuilder().setAccountNumber(2).setAmount(30).build();
        var iterator = this.blockingStub.withdraw(request);
        int count = 0;
        while(iterator.hasNext()) {
            log.info("Received money: {}", iterator.next());
            count ++;
        }
        Assertions.assertEquals(3, count);
    }

    @Test
    public void asyncClientWithdrawTest() {
        var request = WithdrawRequest.newBuilder().setAccountNumber(2).setAmount(30).build();
        var responseObserver = ResponseObserver.<Money>create();
        this.asyncStub.withdraw(request, responseObserver);
        responseObserver.await();
        Assertions.assertEquals(3, responseObserver.getItems().size());
        Assertions.assertNull(responseObserver.throwable());
    }

}
