package com.kawahedukasi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
    public static boolean isStandardNameInput(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z.'` ]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isNumericInput(String input){
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isStandardDobInput(String input){
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isStandardAlphabetInput(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z ]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isGenderCodeInput(String input){
        Pattern pattern = Pattern.compile("^[FMfm]$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
