package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.common.AbstractChannelTest;
import com.learn.javagrpcplayground.common.GrpcServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import sec06.BankServiceGrpc;
import sec06.TransferServiceGrpc;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new BankServiceImpl(), new TransferService());
    protected BankServiceGrpc.BankServiceBlockingStub blockingBankServiceStub;
    protected BankServiceGrpc.BankServiceStub asyncBankServiceStub;
    protected TransferServiceGrpc.TransferServiceStub asyncTransferServiceStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.asyncBankServiceStub = BankServiceGrpc.newStub(channel);
        this.blockingBankServiceStub = BankServiceGrpc.newBlockingStub(channel);
        this.asyncTransferServiceStub = TransferServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }
}
