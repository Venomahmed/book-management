package com.ness.bookmanagement.bookmanagement.config;

import com.ness.bookmanagement.bookmanagement.dto.ApiError;
import com.ness.bookmanagement.bookmanagement.exception.BookManagementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookManagementException.class)
    public ResponseEntity<Object> handleBookManagementException(BookManagementException ex, WebRequest request) {
        // Customize the error response as needed
        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getErrorCode(), errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        // Handle other exceptions (not specifically caught by other handlers)
        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
