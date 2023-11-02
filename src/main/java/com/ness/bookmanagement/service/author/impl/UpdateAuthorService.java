package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Slf4j
@Service
class UpdateAuthorService {
    private final AuthorEntityRepository authorEntityRepository;
    private final ValidationService validationService;

    @Autowired
    UpdateAuthorService(AuthorEntityRepository authorEntityRepository, ValidationService validationService) {
        this.authorEntityRepository = authorEntityRepository;
        this.validationService = validationService;
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(authorNotFoundException(id));

        try {
            boolean isDobAfter = validationService.isDateAfter(updatedAuthorDTO.getDateOfBirth());
            if (isDobAfter) {
                throw new IllegalArgumentException("Author's date of birth is to be in the past");
            }

            authorEntity.setFirstName(updatedAuthorDTO.getFirstName());
            authorEntity.setLastName(updatedAuthorDTO.getLastName());
            authorEntity.setDateOfBirth(updatedAuthorDTO.getDateOfBirth());
            authorEntity.setBiography(updatedAuthorDTO.getBiography());

            authorEntity = authorEntityRepository.save(authorEntity);
            log.info("Author Updated: updatedAuthorId={} ", authorEntity.getId());

            return AuthorDTO.buildDTO(authorEntity);

        } catch (Exception e) {
            log.error("Author Update Failed: message={}", e.getMessage());
            throw new ActionFailedException("Author update failed ID: " + id, e, ErrorCodes.AUTHOR_UPDATE_ERROR);

        } finally {
            log.trace("Request: authorId={} data={} ", id, updatedAuthorDTO);
        }
    }
}
