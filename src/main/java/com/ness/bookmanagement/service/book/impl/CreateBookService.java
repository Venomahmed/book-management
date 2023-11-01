package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.respository.BookEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Slf4j
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
            log.info("Book Created: newBookId={} ", savedBookEntity.getId());

            return BookDTO.buildDTO(savedBookEntity);

        } catch (Exception e) {
            log.error("Book Creation Failed: message={}", e.getMessage());
            throw new ActionFailedException("Book creation failed", e, ErrorCodes.BOOK_CREATION_ERROR);

        } finally {
            log.trace("Book DTO request: data={} ", bookDTO);
        }
    }
}
