package com.ness.bookmanagement.bookmanagement.controller;

import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.service.book.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Book Controller")
@RestController
@RequestMapping("/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Create new Book")
    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(new ApiResponse<>(createdBook), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get list of all Books")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooks() {
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(new ApiResponse<>(allBooks), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Book by book's ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBook(@PathVariable Long id) {
        BookDTO book = bookService.getBook(id);
        return new ResponseEntity<>(new ApiResponse<>(book), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Book by book's ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, updatedBookDTO);
        return new ResponseEntity<>(new ApiResponse<>(updatedBook), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Book by book's ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get list of Books author's firstname & lastname")
    @GetMapping("/by-author")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByAuthorName(@RequestParam String firstName, @RequestParam String lastName) {
        List<BookDTO> booksByAuthorName = bookService.getBooksByAuthorName(firstName, lastName);
        return ResponseEntity.ok(new ApiResponse<>(booksByAuthorName));
    }

    @ApiOperation(value = "Get Book by its ISBN number")
    @GetMapping("/by-isbn")
    public ResponseEntity<ApiResponse<BookDTO>> getByIsbn(@RequestParam String isbn) {
        BookDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(new ApiResponse<>(book));
    }
}
