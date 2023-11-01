package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class CreateAuthorService {
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    CreateAuthorService(AuthorEntityRepository authorEntityRepository) {
        this.authorEntityRepository = authorEntityRepository;
    }

    AuthorDTO createAuthor(AuthorDTO authorDTO) {
        try {
            if (authorDTO == null) {
                throw new IllegalArgumentException("invalid author dto");
            }

            AuthorEntity authorEntity = authorDTO.buildAuthorEntity();
            authorEntity = authorEntityRepository.save(authorEntity);
            log.info("Author Created: newAuthorId={} ", authorEntity.getId());

            return AuthorDTO.buildDTO(authorEntity);

        } catch (Exception e) {
            log.error("Author Creation Failed: message={}", e.getMessage());
            throw new ActionFailedException("Author creation failed", e, ErrorCodes.AUTHOR_CREATION_ERROR);

        } finally {
            log.trace("Author DTO request: data={} ", authorDTO);
        }
    }
}
