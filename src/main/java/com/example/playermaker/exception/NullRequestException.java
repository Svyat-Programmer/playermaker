package com.example.playermaker.exception;

public class NullRequestException extends RuntimeException{
    public NullRequestException(String message) {
        super(message);
    }
}
