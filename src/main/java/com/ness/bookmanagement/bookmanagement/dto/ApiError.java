package com.ness.bookmanagement.bookmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ApiError {
    private final HttpStatus status;
    private int errorCode;
    private final String message;
}
