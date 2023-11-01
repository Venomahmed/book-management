package com.ness.bookmanagement.bookmanagement.service.book.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Service
class CreateBookService {

    private final BookEntityRepository bookEntityRepository;
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    CreateBookService(BookEntityRepository bookEntityRepository, AuthorEntityRepository authorEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
        this.authorEntityRepository = authorEntityRepository;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(authorNotFoundException(bookDTO.getAuthorId()));

        try {
            BookEntity bookEntity = bookDTO.buildBookEntity();
            bookEntity.setId(null);
            bookEntity.setAuthorEntity(authorEntity);

            BookEntity savedBookEntity = bookEntityRepository.save(bookEntity);
            return BookDTO.buildDTO(savedBookEntity);

        } catch (Exception e) {
            throw new ActionFailedException("Book creation failed", e, ErrorCodes.BOOK_CREATION_ERROR);
        }
    }
}
