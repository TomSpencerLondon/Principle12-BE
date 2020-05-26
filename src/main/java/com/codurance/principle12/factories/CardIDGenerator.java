package com.codurance.principle12.factories;

public class CardIDGenerator {
    private static Long currentID = 0L;

    public static Long nextID() {
        return ++currentID;
    }
}
