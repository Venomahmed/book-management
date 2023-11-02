package com.ness.bookmanagement.service;

import java.time.LocalDate;

public interface ValidationService {

    boolean isDateAfter(LocalDate dateToValidate);

    boolean isValidIsbn(String isbn);
}
