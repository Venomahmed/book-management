package com.ness.bookmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(unique = true)
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(length = 500)
    private String summary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;
}
