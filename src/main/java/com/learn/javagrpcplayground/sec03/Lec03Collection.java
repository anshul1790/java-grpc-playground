package com.learn.javagrpcplayground.sec03;

import com.grpc.sec03.collection.service.Book;
import com.grpc.sec03.collection.service.Library;
import com.grpc.sec03.composition.service.Address;
import com.grpc.sec03.composition.service.School;
import com.grpc.sec03.composition.service.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Lec03Collection {
    public static void main(String[] args) {

        // create books
        var book1 = Book.newBuilder()
                .setTitle("harry potter - part1")
                .setAuthor("JK Rowling")
                .setPublicationYear(1997)
                .build();
        var book2 = book1.toBuilder()
                .setTitle("harry potter - part2")
                .setPublicationYear(1998)
                .build();
        var book3 = book1.toBuilder()
                .setTitle("harry potter - part3")
                .setPublicationYear(1999)
                .build();
        var library = Library.newBuilder()
                .setName("fantasy library")
                //.addBooks(book1)
                //.addBooks(book2)
                //.addBooks(book3)
                .addAllBooks(List.of(book1, book2, book3))
                .build();
        log.info("{}", library);
    }
}
