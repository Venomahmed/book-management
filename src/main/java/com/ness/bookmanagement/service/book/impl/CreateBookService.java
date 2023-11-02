package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.respository.BookEntityRepository;
import com.ness.bookmanagement.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.service.author.AuthorUtil.authorNotFoundException;

@Slf4j
@Service
class CreateBookService {
    private final BookEntityRepository bookEntityRepository;
    private final AuthorEntityRepository authorEntityRepository;
    private final ValidationService validationService;

    @Autowired
    CreateBookService(BookEntityRepository bookEntityRepository,
                      AuthorEntityRepository authorEntityRepository,
                      ValidationService validationService) {
        this.bookEntityRepository = bookEntityRepository;
        this.authorEntityRepository = authorEntityRepository;
        this.validationService = validationService;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        AuthorEntity authorEntity = authorEntityRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(authorNotFoundException(bookDTO.getAuthorId()));

        try {
            boolean isDateAfter = validationService.isDateAfter(bookDTO.getPublicationDate());
            if (isDateAfter) {
                throw new IllegalArgumentException("Book's publication date is not be in the future");
            }

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
