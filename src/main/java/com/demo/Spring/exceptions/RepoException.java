package com.demo.Spring.exceptions;

public class RepoException extends RuntimeException {
    public RepoException(String message) {
        super(message);
    }
}