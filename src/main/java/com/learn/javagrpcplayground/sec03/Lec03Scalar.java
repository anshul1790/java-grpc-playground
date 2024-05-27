package com.learn.javagrpcplayground.sec03;

import com.grpc.sec03.person.service.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Lec03Scalar {
    public static void main(String[] args) {
        var person = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(true)
                .setSalary(1000.89)
                .setBankAccountNumber(12345678)
                .setBalance(-34)
                .build();

        log.info("Person is \n{}", person);
    }
}
