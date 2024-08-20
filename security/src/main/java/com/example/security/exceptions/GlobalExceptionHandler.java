package com.example.security.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //  handler Exceptions
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<BindingResult> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getBindingResult());
}

@ExceptionHandler(NoSuchElementException.class)
public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
}

@ExceptionHandler(UserAlreadyExistsExeption.class)
public ResponseEntity<String> handlerUserArlreadyExists(UserAlreadyExistsExeption ex){
    return ResponseEntity.badRequest().body(ex.getMessage());
}

}