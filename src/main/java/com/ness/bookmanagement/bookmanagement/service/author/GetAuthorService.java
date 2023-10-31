package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAuthorService {
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorEntityRepository.findAll();
        return authorEntities.stream()
                .map(AuthorDTO::buildDTO)
                .collect(Collectors.toList());
    }


    public AuthorDTO getAuthorById(Long id) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found (" + id + ")"));
        return AuthorDTO.buildDTO(authorEntity);
    }


}
