package com.pm.auth_service.utils;

public class RequestValidationUtils {

    public static boolean isValidString(String str){
        return str != null && !str.isEmpty();
    }
}
