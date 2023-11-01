package com.ness.bookmanagement.controller;


import com.ness.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.service.author.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Author Controller")
@RestController
@RequestMapping("/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "Create new Author")
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdAuthor));
    }

    @ApiOperation(value = "Get all Authors")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorDTO>>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(authors));
    }

    @ApiOperation(value = "Get Author by author's ID")
    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthorById(@PathVariable Long authorId) {
        AuthorDTO author = authorService.getAuthorById(authorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(author));
    }

    @ApiOperation(value = "Update existing Author by author ID")
    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDTO updatedAuthor) {
        AuthorDTO author = authorService.updateAuthor(authorId, updatedAuthor);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(author));
    }

    @ApiOperation(value = "Delete existing Author by author ID")
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ApiOperation(value = "Get list of Books by author id")
    @GetMapping("/{authorId}/books")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookDTO> books = authorService.getBooksByAuthor(authorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(books));
    }
}

