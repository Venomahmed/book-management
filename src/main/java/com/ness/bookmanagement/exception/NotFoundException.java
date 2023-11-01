package com.ness.bookmanagement.exception;

public class NotFoundException extends BookManagementException {
    private final int errorCode;

    public NotFoundException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}