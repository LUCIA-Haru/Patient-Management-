package com.pm.api_gateway.utils;

public class RequestValidationUtils {

    public static boolean isValidString(String str){
        return str != null && !str.isEmpty();
    }
}
