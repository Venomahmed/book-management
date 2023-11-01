package com.ness.bookmanagement.bookmanagement.service.author.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return AuthorDTO.buildDTO(authorEntity);

        } catch (Exception e) {
            throw new ActionFailedException("Author creation failed", e, ErrorCodes.AUTHOR_CREATION_ERROR);
        }
    }
}
