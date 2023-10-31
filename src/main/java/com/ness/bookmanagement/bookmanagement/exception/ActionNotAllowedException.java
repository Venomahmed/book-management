package com.ness.bookmanagement.bookmanagement.exception;

public abstract class ActionNotAllowedException extends BookManagementException {
    public ActionNotAllowedException() {
        super();
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

}