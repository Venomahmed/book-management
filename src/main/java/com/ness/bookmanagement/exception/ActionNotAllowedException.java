package com.ness.bookmanagement.exception;

public class ActionNotAllowedException extends BookManagementException {
    private final int errorCode;

    public ActionNotAllowedException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ActionNotAllowedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ActionNotAllowedException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}