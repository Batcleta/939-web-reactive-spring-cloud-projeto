package com.example.mercado.exception;

public class CouldNotUpdateException extends RuntimeException {
    public CouldNotUpdateException(String message) {
        super(message);
    }
}
