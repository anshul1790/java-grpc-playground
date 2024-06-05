package com.learn.javagrpcplayground.sec08;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import sec08.GuessNumberGrpc;
import sec08.GuessRequest;
import sec08.GuessResponse;
import sec08.Result;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class GuessService extends GuessNumberGrpc.GuessNumberImplBase {

    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
        return new GuessRequestHandler(responseObserver);
    }

    private static class GuessRequestHandler implements StreamObserver<GuessRequest> {

        private final StreamObserver<GuessResponse> responseObserver;
        private final int secret;
        private int attempt;

        public GuessRequestHandler(StreamObserver<GuessResponse> responseObserver) {
            this.responseObserver = responseObserver;
            this.attempt = 0;
            this.secret = ThreadLocalRandom.current().nextInt(1, 101);
            log.info("Current secret generated is: {}", this.secret);
        }

        @Override
        public void onNext(GuessRequest guessRequest) {
            int guessNumber = guessRequest.getGuess();
            if (guessNumber > secret) {
                sendResponse(Result.TOO_HIGH);
            } else if (guessNumber < secret) {
                sendResponse(Result.TOO_LOW);
            }  else {
                sendResponse(Result.CORRECT);
                responseObserver.onCompleted();
            }
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onCompleted() {
            log.info("---- Stream ended by client ----");
        }

        private void sendResponse(Result result) {
            this.attempt++;
            responseObserver.onNext(GuessResponse.newBuilder()
                    .setAttempt(attempt)
                    .setResult(result).build());
        }
    }
}
