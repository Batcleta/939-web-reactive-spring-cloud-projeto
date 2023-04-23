package com.example.mercado.exception;

public class InvalidParamsException extends RuntimeException {
    public InvalidParamsException(String message) {
        super("Parametros inválidos: " + message);
    }
}
