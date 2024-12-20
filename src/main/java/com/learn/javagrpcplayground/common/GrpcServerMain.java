package com.learn.javagrpcplayground.common;

import com.learn.javagrpcplayground.sec06.BankServiceImpl;
import com.learn.javagrpcplayground.sec06.TransferService;
import com.learn.javagrpcplayground.sec07.FlowControlService;
import com.learn.javagrpcplayground.sec08.GuessService;

public class GrpcServerMain {

    public static void main(String[] args) {
        //GrpcServer.create(new BankServiceImpl(), new TransferService()).start().await();
        //GrpcServer.create(new FlowControlService()).start().await();
        GrpcServer.create(new GuessService()).start().await();
    }

}
