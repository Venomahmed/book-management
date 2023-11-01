package com.ness.bookmanagement.service.book;

import com.ness.bookmanagement.dto.BookDTO;

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
     * @param firstName
     * @param lastName
     * @return
     */
    List<BookDTO> getBooksByAuthorName(String firstName, String lastName);
}
