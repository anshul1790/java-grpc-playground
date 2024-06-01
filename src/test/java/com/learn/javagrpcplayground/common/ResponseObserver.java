package com.learn.javagrpcplayground.common;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class ResponseObserver<T> implements StreamObserver<T> {

    private static final Logger log = LoggerFactory.getLogger(ResponseObserver.class);
    private final List<T> list = Collections.synchronizedList(new ArrayList<>());
    private final CountDownLatch latch;
    private Throwable throwable;

    private ResponseObserver(int countDown) {
        latch = new CountDownLatch(countDown);
    }

    public static <T> ResponseObserver<T> create() {
        return new ResponseObserver<>(1);
    }

    public static <T> ResponseObserver<T> create(int countDown) {
        return new ResponseObserver<>(countDown);
    }

    @Override
    public void onNext(T value) {
        log.info("Received item: {}", value);
        list.add(value);
    }

    @Override
    public void onError(Throwable t) {
        log.info("Received error: {}", t.getMessage());
        this.throwable = t;
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info("Completed");
        this.latch.countDown();
    }

    public void await() {
        try {
            this.latch.await(5, TimeUnit.SECONDS);
        } catch (RuntimeException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getItems () {
        return this.list;
    }

    public Throwable throwable() {
        return this.throwable;
    }

}
