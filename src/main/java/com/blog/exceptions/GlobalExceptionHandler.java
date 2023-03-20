package com.blog.exceptions;


import com.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.client.HttpServerErrorException.InternalServerError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<?> serverExceptionHandler(InternalServerError ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(ex.getMessage(),false));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ex.getMessage(),false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleRestArgumentNotValidException(MethodArgumentNotValidException ex){
        final Map<String,String> errors=new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error->{
            FieldError fieldError = (FieldError) error;
            errors.put(fieldError.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
