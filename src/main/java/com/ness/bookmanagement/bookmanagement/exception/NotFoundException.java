package com.ness.bookmanagement.bookmanagement.exception;

public abstract class NotFoundException extends BookManagementException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}