package com.springboot.milezero.config;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> list = bindingResult.getFieldErrors();

        Map<String, String> map = list.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> (msg1+", "+msg2) // Appends if more than one message exists for a field
                ));

        return ResponseEntity
                .badRequest()
                .body(map);
    }
}
