package tech.ada.pagamento.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.ada.pagamento.Exception.GetSomeMoney;

@RestControllerAdvice
public class PagamentoExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail exceptionHandler(RuntimeException exception) {
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(503), exception.getMessage());
        problemDetails.setTitle("RunTime Exception - Object Not Found");
        return problemDetails;
    }

    @ExceptionHandler(GetSomeMoney.class)
    public ResponseEntity<String> handleNotCreatedException(GetSomeMoney e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // TODO implementar para user not found .. status 404
}
