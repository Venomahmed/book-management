package com.ness.bookmanagement.service.author;

import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.dto.BookDTO;

import java.util.List;

/**
 * Service interface for managing authors.
 */
public interface AuthorService {

    /**
     * Create a new author.
     *
     * @param authorDTO The AuthorDTO object representing the author to be created.
     * @return The created AuthorDTO.
     */
    AuthorDTO createAuthor(AuthorDTO authorDTO);

    /**
     * Retrieve a list of all authors.
     *
     * @return A list of AuthorDTO objects representing all authors.
     */
    List<AuthorDTO> getAllAuthors();

    /**
     * Retrieve an author by their ID.
     *
     * @param id The ID of the author to retrieve.
     * @return The AuthorDTO object representing the retrieved author.
     */
    AuthorDTO getAuthorById(Long id);

    /**
     * Update an author's information.
     *
     * @param id               The ID of the author to be updated.
     * @param updatedAuthorDTO The AuthorDTO object containing updated information.
     * @return The updated AuthorDTO.
     */
    AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO);

    /**
     * Delete an author by their ID.
     *
     * @param id The ID of the author to be deleted.
     */
    void deleteAuthor(Long id);

    /**
     * Retrieve a list of books written by a specific author.
     *
     * @param authorId The ID of the author for whom to retrieve books.
     * @return A list of BookDTO objects representing the books written by the author.
     */
    List<BookDTO> getBooksByAuthor(Long authorId);
}
