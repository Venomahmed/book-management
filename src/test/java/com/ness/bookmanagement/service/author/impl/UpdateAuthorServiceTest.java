package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateAuthorServiceTest {
    private UpdateAuthorService updateAuthorService;
    @Mock
    private AuthorEntityRepository authorEntityRepository;

    @BeforeEach
    void setUp() {
        updateAuthorService = new UpdateAuthorService(authorEntityRepository);
    }

    @Test
    void testUpdateAuthor_whenSuccess() {
        // Given
        AuthorDTO updatedAuthorDTO = AuthorDTO.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author. Updated")
                .build();

        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author.")
                .build();

        when(authorEntityRepository.findById(1L)).thenReturn(Optional.of(authorEntity));
        when(authorEntityRepository.save(authorEntity)).thenReturn(authorEntity);

        // When
        AuthorDTO updatedAuthor = updateAuthorService.updateAuthor(1L, updatedAuthorDTO);

        // Then
        assertThat(updatedAuthor.getId()).isEqualTo(authorEntity.getId());
        assertThat(updatedAuthor.getFirstName()).isEqualTo(updatedAuthorDTO.getFirstName());
        assertThat(updatedAuthor.getLastName()).isEqualTo(updatedAuthorDTO.getLastName());
        assertThat(updatedAuthor.getDateOfBirth()).isEqualTo(updatedAuthorDTO.getDateOfBirth());
        assertThat(updatedAuthor.getBiography()).isEqualTo(updatedAuthorDTO.getBiography());

        verify(authorEntityRepository).save(authorEntity);
    }

    @Test
    void testUpdateAuthor_whenAuthorNotFound_thenAssertNotFoundException() {
        // Given
        Long authorId = 1L;
        AuthorDTO updatedAuthorDTO = AuthorDTO.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author. Updated")
                .build();

        when(authorEntityRepository.findById(authorId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> updateAuthorService.updateAuthor(authorId, updatedAuthorDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Author not found with ID: " + authorId)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_NOT_FOUND);

        verify(authorEntityRepository).findById(authorId);
        verify(authorEntityRepository, never()).save(any(AuthorEntity.class));
    }

    @Test
    void testUpdateAuthor_whenSaveThrowsException_thenAssertNotFoundException() {
        // Given
        Long authorId = 1L;
        AuthorDTO updatedAuthorDTO = AuthorDTO.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .dateOfBirth(LocalDate.now())
                .biography("A famous author. Updated")
                .build();

        AuthorEntity authorEntity = new AuthorEntity();
        when(authorEntityRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));
        when(authorEntityRepository.save(authorEntity)).thenThrow(new RuntimeException("Provoked Error: database error"));

        // When & Then
        assertThatThrownBy(() -> updateAuthorService.updateAuthor(authorId, updatedAuthorDTO))
                .isInstanceOf(ActionFailedException.class)
                .hasMessage("Author update failed ID: " + authorId)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_UPDATE_ERROR);

        verify(authorEntityRepository).findById(authorId);
        verify(authorEntityRepository).save(authorEntity);
    }
}
