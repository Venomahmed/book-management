package com.ness.bookmanagement.controller;

import com.ness.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.dto.BookFilterDTO;
import com.ness.bookmanagement.service.book.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @ApiOperation(value = "Get list of Books author's firstname, lastname or title with pagination")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<BookDTO>>> filterBooks(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam Optional<String> firstName,
                                                                  @RequestParam Optional<String> lastName,
                                                                  @RequestParam Optional<String> title) {

        Pageable pageable = PageRequest.of(page, size);

        BookFilterDTO dto = BookFilterDTO.builder()
                .firstName(firstName.orElse(null))
                .lastName(lastName.orElse(null))
                .title(title.orElse(null))
                .build();

        List<BookDTO> filteredBooks = bookService.filterBooks(pageable, dto);
        return ResponseEntity.ok(new ApiResponse<>(filteredBooks));
    }

    @ApiOperation(value = "Get Book by its ISBN number")
    @GetMapping("/by-isbn")
    public ResponseEntity<ApiResponse<BookDTO>> getByIsbn(@RequestParam String isbn) {
        BookDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(new ApiResponse<>(book));
    }
}
