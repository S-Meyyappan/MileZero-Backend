package com.springboot.milezero.config;

import com.springboot.milezero.dto.response.ErrorResponseDto;
import com.springboot.milezero.exception.ResourceAlreadyExistsException;
import com.springboot.milezero.exception.ResourceNotFoundException;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorResponseDto(e.getMessage())
                );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorResponseDto(e.getMessage())
                );
    }
}
