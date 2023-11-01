package com.ness.bookmanagement.bookmanagement.service.book.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ness.bookmanagement.bookmanagement.service.book.BookUtil.bookNotFoundException;

@Service
class GetBookService {

    private final BookEntityRepository bookEntityRepository;

    @Autowired
    GetBookService(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    public BookDTO getBook(Long id) {
        BookEntity bookEntity = bookEntityRepository.findById(id)
                .orElseThrow(bookNotFoundException(id));

        return BookDTO.buildDTO(bookEntity);
    }
}
