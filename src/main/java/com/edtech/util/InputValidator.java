package com.edtech.util;

public class InputValidator {
    public static boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z0-9_\\- ]{2,}");
    }
    public static boolean isValidId(String id) {
        return id != null && id.matches("[A-Za-z0-9_\\-]{2,}");
    }
    public static boolean isValidAssignmentDetail(String detail) {
        return detail != null && detail.trim().length() > 0 && !detail.trim().equals("");
    }
    public static boolean isValidFilename(String fn) {
        return fn != null && fn.endsWith(".csv");
    }
}