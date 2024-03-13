package com.example.simplebankapp.account.utils;

import java.time.LocalDateTime;

public class AccountUtils {

    public static String generateAccountNumber() {
        int min = 100000;
        int max = 999999;
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String randomNumber = String.valueOf(randNumber);
        return randomNumber;
    }
}
