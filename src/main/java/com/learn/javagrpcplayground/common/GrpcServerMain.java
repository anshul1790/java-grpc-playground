package com.learn.javagrpcplayground.common;

import com.learn.javagrpcplayground.sec06.BankServiceImpl;
import com.learn.javagrpcplayground.sec06.TransferService;

public class GrpcServerMain {

    public static void main(String[] args) {
        GrpcServer.create(new BankServiceImpl(), new TransferService()).start().await();
    }

}
