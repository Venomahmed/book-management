package com.ness.bookmanagement.bookmanagement.controller;

import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(new ApiResponse<>(createdBook), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBook(@PathVariable Long id) {
        BookDTO book = bookService.getBook(id);
        return new ResponseEntity<>(new ApiResponse<>(book), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, updatedBookDTO);
        return new ResponseEntity<>(new ApiResponse<>(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
