package com.ness.bookmanagement.bookmanagement.dto;

import com.ness.bookmanagement.bookmanagement.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String biography;

    /**
     * @param author
     * @return
     */
    public static AuthorDTO buildDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .dateOfBirth(author.getDateOfBirth())
                .biography(author.getBiography())
                .build();
    }
}
