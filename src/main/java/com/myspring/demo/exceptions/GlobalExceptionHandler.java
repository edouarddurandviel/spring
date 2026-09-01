package com.myspring.demo.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    ResponseEntity<ProblemDetail> handleNotFound(
            UserNotFoundException ex) {

        var problem = ProblemDetail.forStatus(
                HttpStatus.NOT_FOUND);

        problem.setTitle("User not found");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problem);
    }
}
