package com.pm.auth_service.exception;

import com.pm.auth_service.response.ApiResponse;
import com.pm.auth_service.utils.constants.Commons;
import com.pm.auth_service.utils.message.ErrorMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handle validation errors (From @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex
            ){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage()));
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                LocalDateTime.now(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex
    ){
        String message = ErrorMessageUtils.ALREADY_EXIST.formatted("Patient","email");
        log.warn(message + " - " + ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
               message,
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ApiResponse<Object>> handleCredentialException(
            InvalidCredentialException ex
    ){
        String message = ErrorMessageUtils.INVALID.formatted("Email or Password");
        log.warn(message + " - " + ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.UNAUTHORIZED.value(),
                message,
                LocalDateTime.now(),
                null
        );
        return  new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidEmailException(
            InvalidEmailException ex
    ){
        String message = ErrorMessageUtils.INVALID.formatted("Email or Password");
        log.warn(message + " - " + ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now(),
                null
        );
        return  new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(
            UserNotFoundException ex
    ){

        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnAuthorizedException(
            UnAuthorizedException ex
    ){
        log.warn(ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtException(
            JwtException ex
    ){
        log.warn(ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return  new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<Object>> handleUnknownException(Throwable ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
