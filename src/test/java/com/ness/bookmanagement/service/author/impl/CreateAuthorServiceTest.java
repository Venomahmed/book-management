package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAuthorServiceTest {
    @Mock
    private AuthorEntityRepository authorEntityRepository;

    private CreateAuthorService createAuthorService;

    @BeforeEach
    void setUp() {
        createAuthorService = new CreateAuthorService(authorEntityRepository);
    }

    @Test
    void testCreateAuthor_whenSuccess() {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author.")
                .build();

        AuthorEntity authorEntity = authorDTO.buildAuthorEntity();
        authorEntity.setId(1L);

        when(authorEntityRepository.save(any(AuthorEntity.class))).thenReturn(authorEntity);

        AuthorDTO createdAuthor = createAuthorService.createAuthor(authorDTO);

        assertThat(createdAuthor.getId()).isEqualTo(authorEntity.getId());
        assertThat(createdAuthor.getFirstName()).isEqualTo(authorDTO.getFirstName());
        assertThat(createdAuthor.getLastName()).isEqualTo(authorDTO.getLastName());
        assertThat(createdAuthor.getDateOfBirth()).isEqualTo(authorDTO.getDateOfBirth());
        assertThat(createdAuthor.getBiography()).isEqualTo(authorDTO.getBiography());

        verify(authorEntityRepository).save(any(AuthorEntity.class));
    }

    @Test
    void testCreateAuthor_whenSaveThrowsException_thenAssertException() {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author.")
                .build();

        when(authorEntityRepository.save(any(AuthorEntity.class)))
                .thenThrow(new RuntimeException("Provoked Error: database not found"));

        assertThatThrownBy(() -> createAuthorService.createAuthor(authorDTO))
                .isInstanceOf(ActionFailedException.class)
                .hasMessage("Author creation failed")
                .hasCauseInstanceOf(RuntimeException.class)
                .hasRootCauseMessage("Provoked Error: database not found")
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_CREATION_ERROR);


        verify(authorEntityRepository).save(any(AuthorEntity.class));
    }

    @Test
    void testCreateAuthor_whenSaveThrowsNullPointerException_thenAssertException() {
        AuthorDTO authorDTO = null;

        assertThatThrownBy(() -> createAuthorService.createAuthor(authorDTO))
                .isInstanceOf(ActionFailedException.class)
                .hasMessage("Author creation failed")
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("invalid author dto")
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_CREATION_ERROR);
    }
}
