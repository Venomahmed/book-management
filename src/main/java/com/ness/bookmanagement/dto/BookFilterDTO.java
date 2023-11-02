package com.ness.bookmanagement.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookFilterDTO {
    private String title;
    private String firstName;
    private String lastName;
}
