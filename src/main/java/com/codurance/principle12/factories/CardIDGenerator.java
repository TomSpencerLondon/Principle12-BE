package com.codurance.principle12.factories;

public class CardIDGenerator {
    private static int currentID = 0;

    public static int nextID() {
        return ++currentID;
    }
}
