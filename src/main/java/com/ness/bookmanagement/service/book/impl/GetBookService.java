package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.dto.BookFilterDTO;
import com.ness.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.respository.BookEntityRepository;
import com.ness.bookmanagement.respository.BookEntityRepositoryImpl;
import com.ness.bookmanagement.service.book.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class GetBookService {

    private final BookEntityRepository bookEntityRepository;
    private final BookEntityRepositoryImpl bookEntityRepositoryImpl;

    @Autowired
    GetBookService(BookEntityRepository bookEntityRepository,
                   BookEntityRepositoryImpl bookEntityRepositoryImpl) {
        this.bookEntityRepository = bookEntityRepository;
        this.bookEntityRepositoryImpl = bookEntityRepositoryImpl;
    }

    public BookDTO getBook(Long id) {
        BookEntity bookEntity = bookEntityRepository.findById(id)
                .orElseThrow(BookUtil.bookNotFoundException(id));

        return BookDTO.buildDTO(bookEntity);
    }

    public BookDTO getBookByIsbn(String isbn) {
        BookEntity bookEntity = bookEntityRepository.findBooksByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book not found with ISBN: " + isbn, ErrorCodes.BOOK_NOT_FOUND));

        return BookDTO.buildDTO(bookEntity);
    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity> booksByAuthorsIn = bookEntityRepository.findAll();

        return booksByAuthorsIn.stream()
                .map(BookDTO::buildDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> filterBooks(BookFilterDTO bookFilterDTO) {
        List<BookEntity> bookEntities = bookEntityRepositoryImpl.searchBooksByCriteria(
                bookFilterDTO.getTitle(),
                bookFilterDTO.getFirstName(),
                bookFilterDTO.getLastName()
        );

        return bookEntities.stream()
                .map(BookDTO::buildDTO)
                .collect(Collectors.toList());
    }
}
