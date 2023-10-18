package com.demo.Spring.controllers;

import com.demo.Spring.exceptions.ApiException;
import com.demo.Spring.exceptions.ConvertionException;
import com.demo.Spring.exceptions.RepoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler({ConvertionException.class, RepoException.class})
    protected ResponseEntity<Map<String, Object>> internalExceptionHandler(RuntimeException e) {
        logger.error("Internal Exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(Map.of("message", "Internal error"));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Map<String, Object>> apiExceptionHandler(ApiException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(e.getStatus()).body(Map.of("message", e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionException( MethodArgumentNotValidException e) {
        logger.error("invalid date: {}", e.getMessage(),e);
        return ResponseEntity.badRequest().body(Map.of("message", Objects.requireNonNull(e.getDetailMessageArguments())));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> genericException(Exception e) {
        logger.error("Generic errorException: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(Map.of("message", "Generic error"));
    }






}