package com.ness.bookmanagement.bookmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String isbn;

    private LocalDate publicationDate;

    @Column(length = 500)
    private String summary;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
