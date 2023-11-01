package com.ness.bookmanagement.bookmanagement.service.author.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ness.bookmanagement.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Service
public class GetBooksByAuthorService {
    private final AuthorEntityRepository authorEntityRepository;
    private final BookEntityRepository bookEntityRepository;

    @Autowired
    GetBooksByAuthorService(AuthorEntityRepository authorEntityRepository, BookEntityRepository bookEntityRepository) {
        this.authorEntityRepository = authorEntityRepository;
        this.bookEntityRepository = bookEntityRepository;
    }

    public List<BookDTO> getBooksByAuthor(Long authorId) {
        AuthorEntity authorEntity = authorEntityRepository.findById(authorId)
                .orElseThrow(authorNotFoundException(authorId));

        List<BookEntity> bookEntities = bookEntityRepository.findByAuthorEntity(authorEntity);
        return bookEntities.stream()
                .map(BookDTO::buildDTO)
                .collect(Collectors.toList());
    }
}
