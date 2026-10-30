package com.myspring.demo.exceptions;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    ResponseEntity<ProblemDetail> handleNotFound(
            UserNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.NOT_FOUND);

        problem.setTitle("User Global Exception Handler");
        problem.setDetail(e.getMessage());
        problem.setProperty("Timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problem);
    }
}
