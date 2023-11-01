package com.ness.bookmanagement.bookmanagement.service.book;

import com.ness.bookmanagement.bookmanagement.dto.BookDTO;

/**
 *
 */
public interface BookService {

    /**
     * @param bookDTO
     * @return
     */
    BookDTO createBook(BookDTO bookDTO);

    /**
     * @param id
     * @return
     */
    BookDTO getBook(Long id);

    /**
     * @param id
     * @param updatedBookDTO
     * @return
     */
    BookDTO updateBook(Long id, BookDTO updatedBookDTO);

    /**
     * @param id
     */
    void deleteBook(Long id);
}
