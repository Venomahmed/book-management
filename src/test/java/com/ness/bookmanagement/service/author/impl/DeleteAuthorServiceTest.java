package com.ness.bookmanagement.service.author.impl;

import com.ness.bookmanagement.constant.ErrorCodes;
import com.ness.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.exception.ActionFailedException;
import com.ness.bookmanagement.exception.NotFoundException;
import com.ness.bookmanagement.respository.AuthorEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAuthorServiceTest {
    private DeleteAuthorService deleteAuthorService;
    @Mock
    private AuthorEntityRepository authorEntityRepository;

    @BeforeEach
    void setUp() {
        deleteAuthorService = new DeleteAuthorService(authorEntityRepository);
    }

    @Test
    void testDeleteAuthor_whenSuccess() {
        // Given
        Long authorId = 1L;
        AuthorEntity authorEntity = new AuthorEntity();
        when(authorEntityRepository.findById(eq(authorId)))
                .thenReturn(java.util.Optional.of(authorEntity));

        // When
        deleteAuthorService.deleteAuthor(authorId);

        // Then
        verify(authorEntityRepository).delete(eq(authorEntity));
    }

    @Test
    void testDeleteAuthor_whenAuthorNotFound_thenAssertNotFoundException() {
        // Given
        Long authorId = 1L;
        when(authorEntityRepository.findById(eq(authorId)))
                .thenReturn(java.util.Optional.empty());

        // When and Then
        assertThatThrownBy(() -> deleteAuthorService.deleteAuthor(authorId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Author not found with ID: " + authorId)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_NOT_FOUND);

        verify(authorEntityRepository, never()).delete(any(AuthorEntity.class));
    }

    @Test
    void testDeleteAuthor_whenDeleteThrowsException_thenAssertException() {
        // Given
        Long authorId = 1L;

        AuthorEntity authorEntity = new AuthorEntity();
        when(authorEntityRepository.findById(eq(authorId)))
                .thenReturn(java.util.Optional.of(authorEntity));

        doThrow(new RuntimeException("Provoked Error: database error"))
                .when(authorEntityRepository).delete(eq(authorEntity));

        // When and Then
        assertThatThrownBy(() -> deleteAuthorService.deleteAuthor(authorId))
                .isInstanceOf(ActionFailedException.class)
                .hasMessage("Author deletion failed ID: " + authorId)
                .hasRootCauseMessage("Provoked Error: database error")
                .hasRootCauseInstanceOf(RuntimeException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCodes.AUTHOR_DELETION_ERROR);
    }
}
