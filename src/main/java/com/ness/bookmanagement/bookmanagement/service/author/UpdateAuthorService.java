package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.entity.Author;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found (" + id + ")"));

        author.setFirstName(updatedAuthorDTO.getFirstName());
        author.setLastName(updatedAuthorDTO.getLastName());
        author.setDateOfBirth(updatedAuthorDTO.getDateOfBirth());
        author.setBiography(updatedAuthorDTO.getBiography());

        author = authorRepository.save(author);

        return AuthorDTO.buildDTO(author);
    }
}
