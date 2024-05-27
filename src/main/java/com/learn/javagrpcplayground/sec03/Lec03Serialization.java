package com.learn.javagrpcplayground.sec03;

import com.grpc.sec03.person.service.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Lec03Serialization {

    private static final Path PATH = Path.of("person.out");

    public static void main(String[] args) throws IOException {
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
        serialize(person);
        log.info("Deserialized person is \n{}",deserialize());
    }

    public static void serialize(Person person) throws IOException {
        try(var stream = Files.newOutputStream(PATH)) {
            person.writeTo(stream);
        }
    }

    public static Person deserialize() throws IOException {
        try(var stream = Files.newInputStream(PATH)) {
            return Person.parseFrom(stream);
        }
    }

}
