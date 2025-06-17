package com.hiagosouza.api.quoted.utils;

public class DocumentUtils {
    public static String cleanDocument(String document) {
        return document == null ? null : document.replaceAll("[\\.\\-/]", "");
    }
}
