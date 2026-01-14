package com.pm.api_gateway.exception;

public class JwtException extends RuntimeException{
    public JwtException(String message){
        super(message);
    }
}
