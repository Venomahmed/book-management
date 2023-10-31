package com.ness.bookmanagement.bookmanagement.dto;

import com.ness.bookmanagement.bookmanagement.entity.BookEntity;
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
    private AuthorDTO authorDTO;


    public static BookDTO buildDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .publicationDate(bookEntity.getPublicationDate())
                .summary(bookEntity.getSummary())
                .authorDTO(AuthorDTO.buildDTO(bookEntity.getAuthorEntity()))
                .build();

    }

    public BookEntity buildBookEntity() {
        return BookEntity.builder()
                .id(this.id)
                .title(this.title)
                .isbn(this.isbn)
                .publicationDate(this.publicationDate)
                .summary(this.summary)
                .authorEntity(this.authorDTO.buildAuthorEntity())
                .build();

    }
}
