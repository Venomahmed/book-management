package com.ness.bookmanagement.bookmanagement.exception;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;

public class BookNotFoundException extends BookManagementException {
    public BookNotFoundException() {
        super();
    }

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getErrorCode() {
        return ErrorCodes.BOOK_NOT_FOUND;
    }
}
