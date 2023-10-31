package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAuthorService {
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    public void deleteAuthor(Long id) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found (" + id + ")"));
        authorEntityRepository.delete(authorEntity);
    }
}
