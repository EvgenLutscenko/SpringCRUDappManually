package com.ua.javarush.exceprion;

public class InvalidRequestArgumentException extends RuntimeException{
    public InvalidRequestArgumentException(String message) {
        super(message);
    }
}
