package com.ness.bookmanagement.bookmanagement.controller;


import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // Create (POST) an Author
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdAuthor));
    }

    // Read (GET) all Authors
    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorDTO>>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(authors));
    }

    // Read (GET) an Author by ID
    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthorById(@PathVariable Long authorId) {
        AuthorDTO author = authorService.getAuthorById(authorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(author));
    }

    // Update (PUT) an Author by ID
    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDTO updatedAuthor) {
        AuthorDTO author = authorService.updateAuthor(authorId, updatedAuthor);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(author));
    }

    // Delete (DELETE) an Author by ID
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // Retrieve books by an Author
    @GetMapping("/{authorId}/books")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookDTO> books = authorService.getBooksByAuthor(authorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(books));
    }
}

