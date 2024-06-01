package com.learn.javagrpcplayground.common;

import com.learn.javagrpcplayground.sec06.BankServiceImpl;

public class GrpcServerMain {

    public static void main(String[] args) {
        GrpcServer.create(new BankServiceImpl()).start().await();
    }

}
