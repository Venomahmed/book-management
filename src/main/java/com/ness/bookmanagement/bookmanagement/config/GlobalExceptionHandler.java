package com.ness.bookmanagement.bookmanagement.config;

import com.ness.bookmanagement.bookmanagement.dto.ApiError;
import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.exception.BookManagementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookManagementException.class)
    public ResponseEntity<ApiError> handleBookManagementException(BookManagementException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(ex.getErrorCode(), errorMessage);
        return ResponseEntity.internalServerError().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ApiError apiError = new ApiError(errorMessage);
        return ResponseEntity.internalServerError().body(apiError);
    }

    @ModelAttribute
    @ResponseBody
    public ResponseEntity<ApiResponse> handleSuccessfulResponse(Object object) {
        return ResponseEntity.ok(new ApiResponse(object));
    }


}
