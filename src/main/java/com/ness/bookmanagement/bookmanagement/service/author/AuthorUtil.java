package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;

import java.util.function.Supplier;

public class AuthorUtil {
    private AuthorUtil() {
    }

    public static Supplier<NotFoundException> authorNotFoundException(long id) {
        return () ->
                new NotFoundException(
                        "Author not found with ID: " + id,
                        ErrorCodes.AUTHOR_NOT_FOUND
                );
    }

}
