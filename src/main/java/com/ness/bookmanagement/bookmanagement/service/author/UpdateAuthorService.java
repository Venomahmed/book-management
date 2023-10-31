package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAuthorService {
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found (" + id + ")"));

        authorEntity.setFirstName(updatedAuthorDTO.getFirstName());
        authorEntity.setLastName(updatedAuthorDTO.getLastName());
        authorEntity.setDateOfBirth(updatedAuthorDTO.getDateOfBirth());
        authorEntity.setBiography(updatedAuthorDTO.getBiography());

        authorEntity = authorEntityRepository.save(authorEntity);

        return AuthorDTO.buildDTO(authorEntity);
    }
}
