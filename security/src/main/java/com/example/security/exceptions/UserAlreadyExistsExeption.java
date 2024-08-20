package com.example.security.exceptions;

public class UserAlreadyExistsExeption extends RuntimeException {
    public UserAlreadyExistsExeption(String message){
        super(message);
    }
}