package com.example.mercado.exception;

public class CouldNotCreateException extends RuntimeException {
    public CouldNotCreateException(String message) {
        super(message);
    }
}
