package com.ness.bookmanagement.bookmanagement.exception;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;

public class AuthorNotFoundException extends BookManagementException {
    public AuthorNotFoundException() {
        super();
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getErrorCode() {
        return ErrorCodes.AUTHOR_NOT_FOUND;
    }
}
