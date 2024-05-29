package com.learn.javagrpcplayground.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import com.learn.javagrpcplayground.sec05.parser.V1Parser;
import com.learn.javagrpcplayground.sec05.parser.V2Parser;
import com.learn.javagrpcplayground.sec05.parser.V3Parser;
import lombok.extern.slf4j.Slf4j;
import sec05.v2.Television;
import sec05.v2.Type;

@Slf4j
public class V2VersionCompatibility {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("samsung")
                .setModel(2019)
                .setType(Type.HD)
                .build();

        // Assume that this class is client and parser on other end is server receiving the messages

        // client 1 sending version 2(sec05.v2) of Television to V1 parser that is still on version 1(sec05.v1)
        V1Parser.parser(tv.toByteArray());

        // client 2 sending version 2(sec05.v2) of Television to V2 parser that is on new version 1(sec05.v2)
        V2Parser.parser(tv.toByteArray());

        V3Parser.parser(tv.toByteArray());

    }
}
