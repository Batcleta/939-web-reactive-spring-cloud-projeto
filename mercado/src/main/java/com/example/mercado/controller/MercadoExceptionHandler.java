package com.example.mercado.controller;

import com.example.mercado.exception.CouldNotCreateException;
import com.example.mercado.exception.CouldNotUpdateException;
import com.example.mercado.exception.InternalServerErrorException;
import com.example.mercado.exception.InvalidParamsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MercadoExceptionHandler {
    @ExceptionHandler(CouldNotCreateException.class)
    public ResponseEntity<String> handleNotCreatedException(CouldNotCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidParamsException.class)
    public ResponseEntity<String> handleInvalidParamsException(InvalidParamsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CouldNotUpdateException.class)
    public ResponseEntity<String> handleNotUpdatedException(CouldNotUpdateException e) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalServerException(InternalServerErrorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
