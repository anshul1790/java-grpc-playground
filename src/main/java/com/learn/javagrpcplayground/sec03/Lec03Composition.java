package com.learn.javagrpcplayground.sec03;

import sec03.composition.service.Address;
import sec03.composition.service.School;
import sec03.composition.service.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Lec03Composition {

    public static void main(String[] args) {
        // create student
        var address = Address.newBuilder()
                .setStreet("123 main st")
                .setCity("Atlanta")
                .setState("GA")
                .build();
        var student = Student.newBuilder()
                .setAddress(address)
                .setName("sam")
                .build();

        // create school
        var school = School.newBuilder()
                .setId(1)
                .setName("high school")
                .setAddress(address.toBuilder().setStreet("234 main street"))
                .build();
        log.info("student: {}", student);
        log.info("school: {}", school);
    }
}
