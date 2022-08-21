package com.paymentsystem.atto.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handler(BadRequest badRequest){
        return ResponseEntity.badRequest().body(badRequest.getMessage());
    }

}
