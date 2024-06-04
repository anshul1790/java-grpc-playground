package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.common.ResponseObserver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec06.AccountBalance;
import sec06.BalanceCheckRequest;

public class UnaryAsyncClientTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(UnaryAsyncClientTest.class);

    @Test
    public void getBalanceTest() {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        // var latch = new CountDownLatch(1);
        var observer = ResponseObserver.<AccountBalance>create(1);
        this.asyncBankServiceStub.getAccountBalance(request, observer);
        observer.await();
        log.info("Items are: {}", observer.getItems().size());

        /*this.asyncStub.getAccountBalance(request, new StreamObserver<>() {
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

        latch.await();*/
    }

}
