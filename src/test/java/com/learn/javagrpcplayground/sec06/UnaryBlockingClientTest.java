package com.learn.javagrpcplayground.sec06;


import com.google.protobuf.Empty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec06.BalanceCheckRequest;

public class UnaryBlockingClientTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(UnaryBlockingClientTest.class);

    @Test
    public void getBalanceTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();

        var balance = this.blockingBankServiceStub.getAccountBalance(request);
        log.info("Unary balance: {}", balance);
        Assertions.assertEquals(100, balance.getBalance());

    }

        @Test
        public void allAccountsTest() {
            var allAccountsResponse = this.blockingBankServiceStub.getAllAccounts(Empty.getDefaultInstance());
            log.info("All accounts size: {}", allAccountsResponse.getAccountsCount());
        }

}
