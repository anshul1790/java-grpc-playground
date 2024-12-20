package com.learn.javagrpcplayground.sec06.repository;

import sec03.collection.service.Collection;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {
    private static final Map<Integer, Integer> db = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toConcurrentMap(
                    Function.identity(),
                    v -> 100
            ));

    public static Integer getBalance(int accountNumber) {
        return db.get(accountNumber);
    }

    public static Map<Integer, Integer> getAllAccounts() {
        return Collections.unmodifiableMap(db);
    }

    public static void deductAmount(int accountNum, int amount) {
        db.computeIfPresent(accountNum, (k, v) -> v - amount);
    }

    public static void addAmount(int accountNum, int amount) {
        db.computeIfPresent(accountNum, (k, v) -> v + amount);
    }
}
