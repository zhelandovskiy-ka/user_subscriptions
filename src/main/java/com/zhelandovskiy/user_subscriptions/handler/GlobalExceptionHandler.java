package com.zhelandovskiy.user_subscriptions.handler;

import com.zhelandovskiy.user_subscriptions.exception.AlreadyExistException;
import com.zhelandovskiy.user_subscriptions.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, Object> body = getBody(httpStatus, request);
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("message", errors);

        return new ResponseEntity<>(body, httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Map<String, Object> body = getBody(httpStatus, request);
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, httpStatus);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException e, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, Object> body = getBody(httpStatus, request);
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, httpStatus);
    }

    private Map<String, Object> getBody(HttpStatus httpStatus, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", httpStatus.value());
        body.put("error", httpStatus.getReasonPhrase());
        body.put("path", request.getDescription(false));

        return body;
    }
}
