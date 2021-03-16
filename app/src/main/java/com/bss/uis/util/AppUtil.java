package com.bss.uis.util;

import android.os.Build.VERSION_CODES;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {
    final static String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static Pattern pattern =  Pattern.compile(PASSWORD_PATTERN);
    private static Matcher matcher;
    public static boolean isCorrectPasswordPolicy(String password)
    {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    @RequiresApi(api = VERSION_CODES.O)
    public static int getAge(int year, int month, int dayOfMonth) {
        return Period.between(
                LocalDate.of(year, month, dayOfMonth),
                LocalDate.now()
        ).getYears();
    }
}
