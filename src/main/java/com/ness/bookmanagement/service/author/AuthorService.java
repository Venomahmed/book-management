package com.ness.bookmanagement.service.author;

import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.dto.BookDTO;

import java.util.List;

/**
 *
 */
public interface AuthorService {

    /**
     * @param authorDTO
     * @return
     */
    AuthorDTO createAuthor(AuthorDTO authorDTO);

    /**
     * @return
     */
    List<AuthorDTO> getAllAuthors();

    /**
     * @param id
     * @return
     */
    AuthorDTO getAuthorById(Long id);

    /**
     * @param id
     * @param updatedAuthorDTO
     * @return
     */
    AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO);

    /**
     * @param id
     */
    void deleteAuthor(Long id);

    /**
     * @param authorId
     * @return
     */
    List<BookDTO> getBooksByAuthor(Long authorId);
}
