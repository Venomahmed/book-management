package com.ness.bookmanagement.bookmanagement.config;

import com.ness.bookmanagement.bookmanagement.dto.ApiError;
import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.exception.ActionNotAllowedException;
import com.ness.bookmanagement.bookmanagement.exception.BookManagementException;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookManagementException.class)
    public ResponseEntity<ApiError> handleBookManagementException(BookManagementException ex) {
        log.error("Failure errorCode=" + ex.getErrorCode(), ex);

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof NotFoundException) {
            statusCode = HttpStatus.NOT_FOUND;

        } else if (ex instanceof ActionNotAllowedException) {
            statusCode = HttpStatus.NOT_ACCEPTABLE;
        }

        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(ex.getErrorCode(), errorMessage);

        return ResponseEntity
                .status(statusCode)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        log.error("Failure =" + ex.getMessage(), ex);

        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(errorMessage);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

    @ModelAttribute
    @ResponseBody
    public ResponseEntity<ApiResponse> handleSuccessfulResponse(Object object) {
        return ResponseEntity.ok(new ApiResponse(object));
    }


}
