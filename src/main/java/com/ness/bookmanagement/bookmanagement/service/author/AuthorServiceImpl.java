package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private CreateAuthorService createAuthorService;

    @Autowired
    private GetAuthorService getAuthorService;

    @Autowired
    private UpdateAuthorService updateAuthorService;

    @Autowired
    private DeleteAuthorService deleteAuthorService;

    @Autowired
    private GetBooksByAuthorService getBooksByAuthorService;

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        return createAuthorService.createAuthor(authorDTO);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return getAuthorService.getAllAuthors();
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return getAuthorService.getAuthorById(id);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        return updateAuthorService.updateAuthor(id, updatedAuthorDTO);
    }

    @Override
    public void deleteAuthor(Long id) {
        deleteAuthorService.deleteAuthor(id);
    }

    @Override
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        return getBooksByAuthorService.getBooksByAuthor(authorId);
    }
}
