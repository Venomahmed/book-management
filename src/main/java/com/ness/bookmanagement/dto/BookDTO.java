package com.ness.bookmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ness.bookmanagement.entity.BookEntity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    private String summary;
    private Long authorId;


    public static BookDTO buildDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .publicationDate(bookEntity.getPublicationDate())
                .summary(bookEntity.getSummary())
                .authorId(bookEntity.getAuthorEntity().getId())
                .build();
    }

    public BookEntity buildBookEntity() {
        return BookEntity.builder()
                .id(this.id)
                .title(this.title)
                .isbn(this.isbn)
                .publicationDate(this.publicationDate)
                .summary(this.summary)
                .build();

    }
}
