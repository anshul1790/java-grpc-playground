package com.learn.javagrpcplayground.sec01;


import com.grpc.sec01.person.service.Person;;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleGrpcDemo {
    public static void main(String[] args) {
        var person = Person.newBuilder().setName("Anshul").setAge(12).build();
        log.info("Name is {} ", person.getName());
    }
}
