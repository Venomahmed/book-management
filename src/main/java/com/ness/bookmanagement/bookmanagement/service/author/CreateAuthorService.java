package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAuthorService {
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .dateOfBirth(authorDTO.getDateOfBirth())
                .biography(authorDTO.getBiography())
                .build();

        authorEntity = authorEntityRepository.save(authorEntity);

        return AuthorDTO.builder()
                .id(authorEntity.getId())
                .firstName(authorEntity.getFirstName())
                .lastName(authorEntity.getLastName())
                .dateOfBirth(authorEntity.getDateOfBirth())
                .biography(authorEntity.getBiography())
                .build();
    }
}
