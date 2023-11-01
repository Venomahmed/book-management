package com.ness.bookmanagement.bookmanagement.service.book.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.exception.ActionNotAllowedException;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ness.bookmanagement.bookmanagement.service.book.BookUtil.bookNotFoundException;

@Slf4j
@Service
class UpdateBookService {
    private final BookEntityRepository bookEntityRepository;

    @Autowired
    UpdateBookService(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    public BookDTO updateBook(Long bookId, BookDTO updatedBookDTO) {
        BookEntity existingBookEntity = bookEntityRepository.findById(bookId)
                .orElseThrow(bookNotFoundException(bookId));

        if (!Objects.equals(updatedBookDTO.getAuthorId(), existingBookEntity.getAuthorEntity().getId())) {
            log.error("Author of the Book cannot be changed bookId={}", bookId);
            throw new ActionNotAllowedException(
                    "Author of the Book cannot be changed BookID: " + bookId,
                    ErrorCodes.BOOK_AUTHOR_CANNOT_BE_CHANGED
            );
        }

        try {
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
