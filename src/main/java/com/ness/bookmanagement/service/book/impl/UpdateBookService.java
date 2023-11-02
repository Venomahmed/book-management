package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.exception.ActionNotAllowedException;
import com.ness.bookmanagement.respository.BookEntityRepository;
import com.ness.bookmanagement.service.ValidationService;
import com.ness.bookmanagement.service.book.BookUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
class UpdateBookService {
    private final BookEntityRepository bookEntityRepository;
    private final ValidationService validationService;

    @Autowired
    UpdateBookService(BookEntityRepository bookEntityRepository, ValidationService validationService) {
        this.bookEntityRepository = bookEntityRepository;
        this.validationService = validationService;
    }

    public BookDTO updateBook(Long bookId, BookDTO updatedBookDTO) {
        BookEntity existingBookEntity = bookEntityRepository.findById(bookId)
                .orElseThrow(BookUtil.bookNotFoundException(bookId));

        if (!Objects.equals(updatedBookDTO.getAuthorId(), existingBookEntity.getAuthorEntity().getId())) {
            log.error("Author of the Book cannot be changed bookId={}", bookId);
            throw new ActionNotAllowedException(
                    "Author of the Book cannot be changed BookID: " + bookId,
                    ErrorCodes.BOOK_AUTHOR_CANNOT_BE_CHANGED
            );
        }

        try {
            boolean isDateAfter = validationService.isDateAfter(updatedBookDTO.getPublicationDate());
            if (isDateAfter) {
                throw new IllegalArgumentException("Book's publication date is not be in the future");
            }

            existingBookEntity.setSummary(updatedBookDTO.getSummary());
            existingBookEntity.setTitle(updatedBookDTO.getTitle());
            existingBookEntity.setIsbn(updatedBookDTO.getIsbn());
            existingBookEntity.setPublicationDate(updatedBookDTO.getPublicationDate());

            BookEntity updatedBookEntity = bookEntityRepository.save(existingBookEntity);
            log.info("Book Updated: updatedBookId={} ", updatedBookEntity.getId());

            return BookDTO.buildDTO(updatedBookEntity);

        } catch (Exception e) {
            log.error("Book Update Failed: message={}", e.getMessage());
            throw new ActionFailedException("Book update failed ID: " + bookId, e, ErrorCodes.BOOK_UPDATE_ERROR);

        } finally {
            log.trace("Request: bookId={} data={} ", bookId, updatedBookDTO);
        }
    }
}
