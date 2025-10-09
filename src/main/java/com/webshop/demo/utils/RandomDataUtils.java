package com.webshop.demo.utils;

import java.util.Random;

public class RandomDataUtils {


    public static String generateRandomEmail() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        int number = random.nextInt(10000);
        return sb.toString() + number + "@example.com";
    }
}
