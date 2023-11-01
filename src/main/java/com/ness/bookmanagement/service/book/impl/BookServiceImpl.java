package com.ness.bookmanagement.service.book.impl;

import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.dto.BookFilterDTO;
import com.ness.bookmanagement.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookServiceImpl implements BookService {
    private final CreateBookService createBookService;
    private final GetBookService getBookService;
    private final UpdateBookService updateBookService;
    private final DeleteBookService deleteBookService;

    @Autowired
    BookServiceImpl(CreateBookService createBookService,
                    GetBookService getBookService,
                    UpdateBookService updateBookService,
                    DeleteBookService deleteBookService) {
        this.createBookService = createBookService;
        this.getBookService = getBookService;
        this.updateBookService = updateBookService;
        this.deleteBookService = deleteBookService;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        return createBookService.createBook(bookDTO);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return getBookService.getAllBooks();
    }

    @Override
    public BookDTO getBook(Long id) {
        return getBookService.getBook(id);
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) {
        return getBookService.getBookByIsbn( isbn);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO updatedBookDTO) {
        return updateBookService.updateBook(id, updatedBookDTO);
    }

    @Override
    public void deleteBook(Long id) {
        deleteBookService.deleteBook(id);
    }

    @Override
    public List<BookDTO> filterBooks(BookFilterDTO bookFilterDTO) {
        return getBookService.filterBooks(bookFilterDTO);
    }
}
