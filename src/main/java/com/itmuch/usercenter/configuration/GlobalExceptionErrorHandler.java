package com.itmuch.usercenter.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e){
        return new ResponseEntity<ErrorBody>(
                ErrorBody.builder()
                        .body("token非法，用户不允许访问")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    static class ErrorBody{
        private String body;
        private int status;
    }
}
