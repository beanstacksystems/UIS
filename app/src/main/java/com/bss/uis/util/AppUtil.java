package com.bss.uis.util;

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
}
