package com.learn.javagrpcplayground.sec06;

import com.google.protobuf.Empty;
import com.learn.javagrpcplayground.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import sec06.AccountBalance;
import sec06.AllAccountsResponse;
import sec06.BalanceCheckRequest;
import sec06.BankServiceGrpc;

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
}
