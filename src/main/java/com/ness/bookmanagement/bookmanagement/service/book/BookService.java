package com.ness.bookmanagement.bookmanagement.service.book;

import com.ness.bookmanagement.bookmanagement.dto.BookDTO;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO);

    BookDTO getBook(Long id);

    BookDTO updateBook(Long id, BookDTO updatedBookDTO);

    void deleteBook(Long id);
}
