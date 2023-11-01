package com.ness.bookmanagement.bookmanagement.service.author.impl;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AuthorServiceImpl implements AuthorService {
    private final CreateAuthorService createAuthorService;
    private final GetAuthorService getAuthorService;
    private final UpdateAuthorService updateAuthorService;
    private final DeleteAuthorService deleteAuthorService;
    private final GetBooksByAuthorService getBooksByAuthorService;

    @Autowired
    AuthorServiceImpl(CreateAuthorService createAuthorService,
                      GetAuthorService getAuthorService,
                      UpdateAuthorService updateAuthorService,
                      DeleteAuthorService deleteAuthorService,
                      GetBooksByAuthorService getBooksByAuthorService) {
        this.createAuthorService = createAuthorService;
        this.getAuthorService = getAuthorService;
        this.updateAuthorService = updateAuthorService;
        this.deleteAuthorService = deleteAuthorService;
        this.getBooksByAuthorService = getBooksByAuthorService;
    }

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
