package com.learn.javagrpcplayground.sec05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import sec05.v2.Television;

@Slf4j
public class V2Parser {

    public static void parser(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        log.info("Brand: {}", tv.getBrand());
        log.info("Year: {}", tv.getModel());
        log.info("Year: {}", tv.getType());
    }
}
