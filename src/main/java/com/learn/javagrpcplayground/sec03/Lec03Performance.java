package com.learn.javagrpcplayground.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.grpc.sec03.person.service.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class Lec03Performance {

    private static final Path PATH = Path.of("person.out");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        var protoPerson = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(true)
                .setSalary(1000.89)
                .setBankAccountNumber(12345678)
                .setBalance(-34)
                .build();
        log.info("Person is \n{}", protoPerson);

        var jsonPerson = new JsonPerson(
                "sam", 12, "sam@gmail.com", true,
                1000.89, 12345678, -34);

        for (int i = 0; i < 5; i++) {
            runTest("jsonTest", () -> json(jsonPerson));
            runTest("protoTest", () -> proto(protoPerson));
        }

    }

    private static void proto(Person person) {
        var bytes = person.toByteArray();
        try {
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson jsonPerson) {
        try {
            var bytes = mapper.writeValueAsBytes(jsonPerson);
            mapper.readValue(bytes, JsonPerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void runTest(String testName, Runnable runnable) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < 5_000_000; i++) {
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("Time take for {} - {} ms", testName, (end-start));
    }

}
