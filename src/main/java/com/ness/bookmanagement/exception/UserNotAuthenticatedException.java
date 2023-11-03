package com.ness.bookmanagement.exception;

import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends BookManagementException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
