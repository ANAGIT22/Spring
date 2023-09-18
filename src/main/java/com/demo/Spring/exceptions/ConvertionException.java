package com.demo.Spring.exceptions;

public class ConvertionException extends RuntimeException {

    public ConvertionException(Throwable cause) {
        super("Error Mapping Event", cause);
    }
}