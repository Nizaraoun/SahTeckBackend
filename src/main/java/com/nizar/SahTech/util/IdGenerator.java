package com.nizar.SahTech.util;

import java.util.Random;


public class IdGenerator {
    public static String generateId() {
        final Random random = new Random();
        final String numbers = "0123456789";
        final String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        final String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String allChars = numbers + lowercaseLetters + uppercaseLetters;

        StringBuilder value = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            value.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return value.toString();
    }
}