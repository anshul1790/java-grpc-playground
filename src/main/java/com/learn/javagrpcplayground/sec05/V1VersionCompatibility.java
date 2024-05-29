package com.learn.javagrpcplayground.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import com.learn.javagrpcplayground.sec05.parser.V1Parser;
import com.learn.javagrpcplayground.sec05.parser.V2Parser;
import com.learn.javagrpcplayground.sec05.parser.V3Parser;
import lombok.extern.slf4j.Slf4j;
import sec05.v1.Television;

@Slf4j
public class V1VersionCompatibility {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("Samsung")
                .setYear(2019)
                .build();
        V1Parser.parser((tv.toByteArray()));
        V2Parser.parser(tv.toByteArray());
        V3Parser.parser(tv.toByteArray());
    }
}
