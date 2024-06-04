package com.learn.javagrpcplayground.sec06.requesthandlers;

import com.learn.javagrpcplayground.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import sec06.AccountBalance;
import sec06.TransferRequest;
import sec06.TransferResponse;
import sec06.TransferStatus;

@Slf4j
public class TransferRequestHandler implements StreamObserver<TransferRequest> {

    private final StreamObserver<TransferResponse> responseObserver;

    public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }


    @Override
    public void onNext(TransferRequest transferRequest) {
        var status = this.transfer(transferRequest);
        var response = TransferResponse.newBuilder()
                .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                .setStatus(status)
                .build();
        this.responseObserver.onNext(response);
        /*if (TransferStatus.COMPLETED.equals(status)) {
            var response = TransferResponse.newBuilder()
                    .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                    .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                    .setStatus(status)
                    .build();
            this.responseObserver.onNext(response);
        }*/
    }

    @Override
    public void onError(Throwable t) {
        log.info("Client error: {}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Transfer request stream completed");
        this.responseObserver.onCompleted();
    }


    private TransferStatus transfer(TransferRequest transferRequest) {
        var amount = transferRequest.getAmount();
        var fromAccount = transferRequest.getFromAccount();
        var toAccount = transferRequest.getToAccount();
        var status = TransferStatus.REJECTED;
        if (AccountRepository.getBalance(fromAccount) >= amount && (fromAccount != toAccount)) {
            AccountRepository.deductAmount(fromAccount, amount);
            AccountRepository.addAmount(toAccount, amount);
            status = TransferStatus.COMPLETED;
        }
        log.info("Transfer status is: {}", status);
        return status;
    }


    private AccountBalance toAccountBalance(int accountNumber) {
        return AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();
    }
}
