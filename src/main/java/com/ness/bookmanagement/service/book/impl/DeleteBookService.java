package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.BookEntityRepository;
import com.ness.bookmanagement.service.book.BookUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(BookUtil.bookNotFoundException(id));

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
