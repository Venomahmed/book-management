package com.ness.bookmanagement.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isDateAfter(LocalDate dateToValidate) {
        return dateToValidate.isAfter(LocalDate.now());
    }

    @Override
    public boolean isValidIsbn(String isbn) {
        // TODO: cannot implement due to lack of time
        return true;
    }

}
