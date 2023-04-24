package com.example.mercado.controller;

import com.example.mercado.exception.CouldNotCreateException;
import com.example.mercado.exception.CouldNotUpdateException;
import com.example.mercado.exception.InternalServerErrorException;
import com.example.mercado.exception.InvalidParamsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MercadoExceptionHandler {

    @ExceptionHandler(CouldNotCreateException.class)
    public ProblemDetail handleNotCreatedException(CouldNotCreateException e) {
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
        problemDetails.setTitle("RunTime Exception - Could not create");
        return problemDetails;
    }

    @ExceptionHandler(InvalidParamsException.class)
    public ProblemDetail handleInvalidParamsException(InvalidParamsException e) {
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
        problemDetails.setTitle("RunTime Exception - Invalid Params");
        return problemDetails;
    }

    @ExceptionHandler(CouldNotUpdateException.class)
    public ProblemDetail handleNotUpdatedException(CouldNotUpdateException e) {
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(418), e.getMessage());
        problemDetails.setTitle("RunTime Exception - Could not update");
        return problemDetails;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ProblemDetail handleInternalServerException(InternalServerErrorException e) {
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
        problemDetails.setTitle("RunTime Exception - Internal Server Error");
        return problemDetails;
    }
}
