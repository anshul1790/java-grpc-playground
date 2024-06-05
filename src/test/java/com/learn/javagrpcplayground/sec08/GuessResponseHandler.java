package com.learn.javagrpcplayground.sec08;

import com.learn.javagrpcplayground.sec07.ResponseHandler;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec07.RequestSize;
import sec08.GuessNumberGrpc;
import sec08.GuessRequest;
import sec08.GuessResponse;

import java.util.concurrent.CountDownLatch;

public class GuessResponseHandler implements StreamObserver<GuessResponse> {

    private static final Logger log = LoggerFactory.getLogger(GuessResponseHandler.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private StreamObserver<GuessRequest> guessRequestStreamObserver;
    private int lower;
    private int upper;
    private int middle;

    @Override
    public void onNext(GuessResponse guessResponse) {
        log.info("Guess response -> attempt: {}, response: {}",
                guessResponse.getAttempt(), guessResponse.getResult());
        switch (guessResponse.getResult()) {
            case TOO_LOW -> this.send(this.middle, this.upper);
            case TOO_HIGH -> this.send(this.lower, this.middle);
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        this.guessRequestStreamObserver.onCompleted();
        latch.countDown();
    }

    private void send(int low, int high) {
        this.lower = low;
        this.upper = high;
        this.middle = (low + high) / 2;
        log.info("Client guess is: {}", this.middle);
        this.guessRequestStreamObserver.onNext(
                GuessRequest.newBuilder().setGuess(this.middle).build());
    }

    public void start() {
        this.send(0, 100);
    }

    public void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGuessRequestStreamObserver(StreamObserver<GuessRequest> guessRequestStreamObserver) {
        this.guessRequestStreamObserver = guessRequestStreamObserver;
    }
}
