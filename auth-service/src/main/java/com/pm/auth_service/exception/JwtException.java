package com.pm.auth_service.exception;

public class JwtException extends RuntimeException{
    public JwtException(String message){
        super(message);
    }
}
