package com.example.playermaker.exception;

public class EmptyPlayersListException extends RuntimeException {
    public EmptyPlayersListException(String message) {
        super(message);
    }
}
