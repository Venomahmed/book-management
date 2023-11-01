package com.ness.bookmanagement.bookmanagement.service.author.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Service
class UpdateAuthorService {
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    UpdateAuthorService(AuthorEntityRepository authorEntityRepository) {
        this.authorEntityRepository = authorEntityRepository;
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(authorNotFoundException(id));

        try {
            authorEntity.setFirstName(updatedAuthorDTO.getFirstName());
            authorEntity.setLastName(updatedAuthorDTO.getLastName());
            authorEntity.setDateOfBirth(updatedAuthorDTO.getDateOfBirth());
            authorEntity.setBiography(updatedAuthorDTO.getBiography());

            authorEntity = authorEntityRepository.save(authorEntity);

            return AuthorDTO.buildDTO(authorEntity);

        } catch (Exception e) {
            throw new ActionFailedException("Author update failed ID: " + id, e, ErrorCodes.AUTHOR_UPDATE_ERROR);
        }
    }
}
