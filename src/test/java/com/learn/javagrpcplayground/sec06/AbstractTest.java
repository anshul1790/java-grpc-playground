package com.learn.javagrpcplayground.sec06;

import com.learn.javagrpcplayground.common.AbstractChannelTest;
import com.learn.javagrpcplayground.common.GrpcServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import sec06.BankServiceGrpc;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new BankServiceImpl());
    protected BankServiceGrpc.BankServiceBlockingStub blockingStub;
    protected BankServiceGrpc.BankServiceStub asyncStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.asyncStub = BankServiceGrpc.newStub(channel);
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }
}
