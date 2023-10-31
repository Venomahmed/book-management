package com.ness.bookmanagement.bookmanagement.service.author;

import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.AuthorNotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetBooksByAuthorService {
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    @Autowired
    private BookEntityRepository bookEntityRepository;

    public List<BookDTO> getBooksByAuthor(Long authorId) {
        AuthorEntity authorEntity = authorEntityRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("author not found (" + authorId + ")"));

        List<BookEntity> bookEntities = bookEntityRepository.findByAuthorEntity(authorEntity);
        return bookEntities.stream()
                .map(BookDTO::buildDTO)
                .collect(Collectors.toList());
    }
}
