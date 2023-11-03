package com.ness.bookmanagement.config;

import com.ness.bookmanagement.dto.ApiError;
import com.ness.bookmanagement.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookManagementException.class)
    public ResponseEntity<ApiError> handleBookManagementException(BookManagementException ex) {
        log.error("Request Failure: errorCode={}, message={} ", ex.getErrorCode(), ex.getMessage());

        HttpStatus statusCode;

        if (ex instanceof UserNotAuthenticatedException) {
            statusCode = HttpStatus.UNAUTHORIZED;

        } else if (ex instanceof NotFoundException) {
            statusCode = HttpStatus.NOT_FOUND;

        } else if (ex instanceof ActionNotAllowedException) {
            statusCode = HttpStatus.NOT_ACCEPTABLE;

        } else if (ex instanceof ActionFailedException) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        } else {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ApiError apiError = new ApiError(ex.getErrorCode(), ex.getMessage());

        return ResponseEntity
                .status(statusCode)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        log.error("Request Failure: message={} ", ex.getMessage());

        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(errorMessage);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

}
