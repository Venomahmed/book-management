package com.ness.bookmanagement.bookmanagement.exception;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;

public class CannotChangeAuthorException extends ActionNotAllowedException {
    public CannotChangeAuthorException() {
        super();
    }

    public CannotChangeAuthorException(String message) {
        super(message);
    }

    public CannotChangeAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getErrorCode() {
        return ErrorCodes.CANNOT_CHANGE_AUTHOR;
    }
}
