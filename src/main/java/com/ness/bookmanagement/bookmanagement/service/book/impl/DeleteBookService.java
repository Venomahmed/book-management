package com.ness.bookmanagement.bookmanagement.service.book.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.bookmanagement.service.book.BookUtil.bookNotFoundException;

@Slf4j
@Service
class DeleteBookService {

    private final BookEntityRepository bookEntityRepository;

    @Autowired
    DeleteBookService(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    public void deleteBook(Long id) {
        BookEntity bookEntity = bookEntityRepository.findById(id)
                .orElseThrow(bookNotFoundException(id));

        try {
            bookEntityRepository.delete(bookEntity);
            log.info("Book Deleted: bookId={} ", bookEntity.getId());

        } catch (Exception e) {
            log.error("Book Deletion Failed: message={} ", e.getMessage());
            throw new ActionFailedException("Book deletion failed ID: " + id, e, ErrorCodes.BOOK_DELETION_ERROR);

        } finally {
            log.trace("Request: bookId={} ", id);
        }
    }
}
