package com.learn.javagrpcplayground.sec07;

import com.learn.javagrpcplayground.common.AbstractChannelTest;
import com.learn.javagrpcplayground.common.GrpcServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sec07.FlowControlServiceGrpc;

public class FlowControlTest extends AbstractChannelTest {
    private final GrpcServer server = GrpcServer.create(new FlowControlService());
    private FlowControlServiceGrpc.FlowControlServiceStub flowControlServiceStub;

    @BeforeAll
    public void setUp() {
        this.server.start();
        this.flowControlServiceStub = FlowControlServiceGrpc.newStub(channel);
    }

    @Test
    public void flowControlDemo() {
        var responseObserver = new ResponseHandler();
        var requestObserver = this.flowControlServiceStub.getMessages(responseObserver);
        responseObserver.setRequestObserver(requestObserver);
        responseObserver.start();
        responseObserver.await();
    }

    @AfterAll
    public void stop() {
        this.server.stop();
    }
}
