package com.ness.bookmanagement.bookmanagement.service.author.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Service
class DeleteAuthorService {
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    DeleteAuthorService(AuthorEntityRepository authorEntityRepository) {
        this.authorEntityRepository = authorEntityRepository;
    }

    public void deleteAuthor(Long id) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(authorNotFoundException(id));

        try {
            authorEntityRepository.delete(authorEntity);

        } catch (Exception e) {
            throw new ActionFailedException("Author deletion failed ID: " + id, e, ErrorCodes.AUTHOR_DELETION_ERROR);
        }
    }
}
