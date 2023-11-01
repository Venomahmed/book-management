package com.ness.bookmanagement.bookmanagement.service.book.impl;

import com.ness.bookmanagement.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
import com.ness.bookmanagement.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.respository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.ness.bookmanagement.bookmanagement.service.book.BookUtil.bookNotFoundException;

@Service
class GetBookService {

    private final BookEntityRepository bookEntityRepository;
    private final AuthorEntityRepository authorEntityRepository;

    @Autowired
    GetBookService(BookEntityRepository bookEntityRepository, AuthorEntityRepository authorEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
        this.authorEntityRepository = authorEntityRepository;
    }

    public BookDTO getBook(Long id) {
        BookEntity bookEntity = bookEntityRepository.findById(id)
                .orElseThrow(bookNotFoundException(id));

        return BookDTO.buildDTO(bookEntity);
    }

    public List<BookDTO> getBooksByAuthorName(String firstName, String lastName) {
        List<AuthorEntity> authorEntities = authorEntityRepository.findByFirstNameIgnoreCaseOrLastNameIgnoreCase(firstName, lastName);

        List<BookEntity> booksByAuthorsIn = bookEntityRepository.findBooksByAuthorsIn(new HashSet<>(authorEntities));

        return booksByAuthorsIn.stream()
                .map(BookDTO::buildDTO)
                .collect(Collectors.toList());
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
}
