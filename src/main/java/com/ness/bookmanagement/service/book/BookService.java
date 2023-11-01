package com.ness.bookmanagement.service.book;

import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.dto.BookFilterDTO;

import java.util.List;

/**
 * Service interface for managing books.
 */
public interface BookService {

    /**
     * Creates a new book.
     *
     * @param bookDTO The book data to be created.
     * @return The created book DTO.
     */
    BookDTO createBook(BookDTO bookDTO);

    /**
     * Retrieves all books.
     *
     * @return A list of all book DTOs.
     */
    List<BookDTO> getAllBooks();

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id The ID of the book to retrieve.
     * @return The book DTO with the specified ID.
     */
    BookDTO getBook(Long id);

    /**
     * Retrieves a book by its ISBN (International Standard Book Number).
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return The book DTO with the specified ISBN.
     */
    BookDTO getBookByIsbn(String isbn);

    /**
     * Updates an existing book with new data.
     *
     * @param id             The ID of the book to update.
     * @param updatedBookDTO The updated book data.
     * @return The updated book DTO.
     */
    BookDTO updateBook(Long id, BookDTO updatedBookDTO);

    /**
     * Deletes a book by its unique identifier.
     *
     * @param id The ID of the book to delete.
     */
    void deleteBook(Long id);

    /**
     * Filters books based on the specified criteria in the provided filter DTO.
     *
     * @param bookFilterDTO The filter criteria to apply.
     * @return A list of book DTOs that match the filter criteria.
     */
    List<BookDTO> filterBooks(BookFilterDTO bookFilterDTO);
}
