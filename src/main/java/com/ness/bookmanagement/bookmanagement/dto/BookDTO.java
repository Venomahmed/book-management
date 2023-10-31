package com.ness.bookmanagement.bookmanagement.dto;

import com.ness.bookmanagement.bookmanagement.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private String summary;
    
    // You can also include author information here as a simplified representation
    private AuthorDTO author;



    // Helper method to convert Book to BookDTO
    public static BookDTO buildDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .summary(book.getSummary())
                .author(AuthorDTO.buildDTO(book.getAuthor()))
                .build();

    }
}
