package com.learn.javagrpcplayground.sec08;

import com.learn.javagrpcplayground.common.AbstractChannelTest;
import com.learn.javagrpcplayground.common.GrpcServer;
import com.learn.javagrpcplayground.sec07.FlowControlService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import sec07.FlowControlServiceGrpc;
import sec08.GuessNumberGrpc;

public class GuessNumberTest extends AbstractChannelTest {
    private final GrpcServer server = GrpcServer.create(new GuessService());
    private GuessNumberGrpc.GuessNumberStub guessNumberStub;

    @BeforeAll
    public void setUp() {
        this.server.start();
        this.guessNumberStub = GuessNumberGrpc.newStub(channel);
    }


    @RepeatedTest(5)
    public void makeGuessTest() {
        var responseObserver = new GuessResponseHandler();
        var requestObserver = this.guessNumberStub.makeGuess(
                responseObserver
        );
        responseObserver.setGuessRequestStreamObserver(requestObserver);
        responseObserver.start();
        responseObserver.await();
        System.out.println("------------------------------------------------");
    }

    @AfterAll
    public void close() {
        this.server.stop();
    }

}
