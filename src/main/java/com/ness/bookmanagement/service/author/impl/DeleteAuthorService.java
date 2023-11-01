package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Slf4j
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
            log.info("Author Deleted: authorId={} ", authorEntity.getId());

        } catch (Exception e) {
            log.error("Author Deletion Failed: message={} ", e.getMessage());
            throw new ActionFailedException("Author deletion failed ID: " + id, e, ErrorCodes.AUTHOR_DELETION_ERROR);

        } finally {
            log.trace("Request: authorId={} ", id);
        }
    }
}
