package com.ness.bookmanagement.bookmanagement.exception;

public abstract class BookManagementException extends RuntimeException {
    public BookManagementException() {
        super();
    }

    public BookManagementException(String message) {
        super(message);
    }

    public BookManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getErrorCode();
}