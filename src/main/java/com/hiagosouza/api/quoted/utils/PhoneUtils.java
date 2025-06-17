package com.hiagosouza.api.quoted.utils;

public class PhoneUtils {
    public static String cleanPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        return phoneNumber.replaceAll("[^\\d]", "");
    }
}
