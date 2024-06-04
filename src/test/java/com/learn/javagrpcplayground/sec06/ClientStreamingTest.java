package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec06.AccountBalance;
import sec06.DepositRequest;
import sec06.Money;

import java.util.stream.IntStream;

public class ClientStreamingTest extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(ClientStreamingTest.class);

    @Test
    public void depositTest() {
        // Building the responseServer object that will send the account balance as mentioned in proto file
        // this is what we expect from the server to return, this is handled separately when a response is received
        var responseObserver = ResponseObserver.<AccountBalance>create();
        // the request observer is going to be the stream that a client server will handle.
        // As we know that client needs to send a stream, this is how it is being handled below
        var requestObserver = this.asyncBankServiceStub.deposit(responseObserver);

        // this will interact with client server and keep on sending the messages
        // First using onNext it will create a initial stream by setting account num
        // and then IntStream to keep on sending the messages to same stream
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());
        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
                .forEach(requestObserver::onNext);
        requestObserver.onCompleted();

        // at this point we will recieve some response from server
        responseObserver.await();
        Assertions.assertEquals(1, responseObserver.getItems().size());
        Assertions.assertEquals(200, responseObserver.getItems().get(0).getBalance());
        Assertions.assertNull(responseObserver.throwable());

    }

}
