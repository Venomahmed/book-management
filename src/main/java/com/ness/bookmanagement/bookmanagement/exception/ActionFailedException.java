package com.ness.bookmanagement.bookmanagement.exception;

public class ActionFailedException extends BookManagementException {
    private final int errorCode;

    public ActionFailedException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ActionFailedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ActionFailedException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

}