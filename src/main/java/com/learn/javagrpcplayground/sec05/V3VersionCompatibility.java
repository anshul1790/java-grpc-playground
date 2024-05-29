package com.learn.javagrpcplayground.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import com.learn.javagrpcplayground.sec05.parser.V1Parser;
import com.learn.javagrpcplayground.sec05.parser.V2Parser;
import com.learn.javagrpcplayground.sec05.parser.V3Parser;
import lombok.extern.slf4j.Slf4j;
import sec05.v3.Television;
import sec05.v3.Type;

@Slf4j
public class V3VersionCompatibility {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("samsung")
                .setType(Type.HD)
                .build();

        // Assume that this class is client and parser on other end is server receiving the messages

        V1Parser.parser(tv.toByteArray());

        V2Parser.parser(tv.toByteArray());

        V3Parser.parser(tv.toByteArray());

    }
}
