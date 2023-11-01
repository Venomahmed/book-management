package com.ness.bookmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ness.bookmanagement.entity.AuthorEntity;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String biography;

    public static AuthorDTO buildDTO(AuthorEntity authorEntity) {
        return AuthorDTO.builder()
                .id(authorEntity.getId())
                .firstName(authorEntity.getFirstName())
                .lastName(authorEntity.getLastName())
                .dateOfBirth(authorEntity.getDateOfBirth())
                .biography(authorEntity.getBiography())
                .build();
    }

    public AuthorEntity buildAuthorEntity() {
        return AuthorEntity.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .biography(this.biography)
                .build();

    }
}
