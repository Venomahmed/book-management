package com.ness.bookmanagement.bookmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 1000)
    private String firstName;

    @Column(name = "last_name", length = 1000)
    private String lastName;

    @Column(name = "date_of_birth", length = 1000)
    private LocalDate dateOfBirth;

    @Column(length = 1000)
    private String biography;

    @OneToMany(mappedBy = "authorEntity",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<BookEntity> bookEntities;
}
