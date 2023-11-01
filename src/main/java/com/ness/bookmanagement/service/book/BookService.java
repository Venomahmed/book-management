package com.ness.bookmanagement.service.book;

import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.dto.BookFilterDTO;

import java.util.List;

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
     * @return
     */
    List<BookDTO> getAllBooks();

    /**
     * @param id
     * @return
     */
    BookDTO getBook(Long id);

    /**
     * @param isbn
     * @return
     */
    BookDTO getBookByIsbn(String isbn);

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

    /**
     * @param bookFilterDTO
     * @return
     */
    List<BookDTO> filterBooks(BookFilterDTO bookFilterDTO);
}
