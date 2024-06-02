package com.learn.javagrpcplayground.sec06;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import com.learn.javagrpcplayground.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import sec06.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {

        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                        .setBalance(balance)
                                .build();

        // to send a response back to our server - which will send it further to client
        responseObserver.onNext(accountBalance);

        // to tell our server that service has completed processing the request
        responseObserver.onCompleted();

        // to tell our server that while processing request we have encountered an error
        //responseObserver.onError();

    }


    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
        var accounts = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(e -> AccountBalance.newBuilder().setAccountNumber(e.getKey()).setBalance(e.getValue()).build())
                .toList();
        var response = AllAccountsResponse.newBuilder().addAllAccounts(accounts).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount();
        var accountBalance = AccountRepository.getBalance(accountNumber);

        if (requestedAmount > accountBalance) {
            responseObserver.onCompleted(); // we will change it to error message later
            return;
        }

        for (int i = 0; i < (requestedAmount / 10); i++) {
            var money = Money.newBuilder().setAmount(10).build();
            responseObserver.onNext(money);
            log.info("Money sent: {}", money);
            AccountRepository.deductAmount(accountNumber, 10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        responseObserver.onCompleted();
    }

}
