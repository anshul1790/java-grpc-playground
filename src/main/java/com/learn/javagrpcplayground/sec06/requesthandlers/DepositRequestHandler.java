package com.learn.javagrpcplayground.sec06.requesthandlers;

import com.learn.javagrpcplayground.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import sec06.AccountBalance;
import sec06.DepositRequest;

@Slf4j
public class DepositRequestHandler implements StreamObserver<DepositRequest> {

    private final StreamObserver<AccountBalance> responseObserver;
    private int accountNumber;

    public DepositRequestHandler(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        switch (depositRequest.getRequestCase()) {
            case ACCOUNT_NUMBER -> this.accountNumber = depositRequest.getAccountNumber();
            case MONEY -> AccountRepository.addAmount(this.accountNumber, depositRequest.getMoney().getAmount());
        }
    }

    @Override
    public void onError(Throwable t) {
        log.info("client error: {}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();
        this.responseObserver.onNext(accountBalance);
        this.responseObserver.onCompleted();
    }
}
