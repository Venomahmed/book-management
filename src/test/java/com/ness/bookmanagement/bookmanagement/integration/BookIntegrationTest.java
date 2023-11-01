package com.ness.bookmanagement.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.entity.AuthorEntity;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorEntityRepository authorEntityRepository;

    private AuthorEntity createdAuthor;

    @BeforeEach
    public void beforeEach() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .biography("Author biography")
                .build();

        createdAuthor = authorEntityRepository.save(authorEntity);

        assertThat(createdAuthor).isNotNull();
    }

    @Test
    public void givenBookDTO_withValidAuthorEntity_thenVerifyCreateAndGetEndpoint() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("Sample Book")
                .isbn("1111111111")
                .publicationDate(LocalDate.now())
                .summary("This is a sample book.")
                .authorId(createdAuthor.getId())
                .build();

        String bookResponseAsString = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.title").value("Sample Book"))
                .andExpect(jsonPath("$.data.isbn").value("1111111111"))
                .andExpect(jsonPath("$.data.publicationDate").value(LocalDate.now().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer newBookId = JsonPath.read(bookResponseAsString, "$.data.id");

        mockMvc.perform(get("/books/{id}", newBookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(newBookId))
                .andExpect(jsonPath("$.data.title").value("Sample Book"))
                .andExpect(jsonPath("$.data.isbn").value("1111111111"))
                .andExpect(jsonPath("$.data.publicationDate").value(LocalDate.now().toString()));
    }

    @Test
    public void givenBookDTO_withValidAuthorEntity_thenVerifyUpdateAndDeleteBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("Sample Book")
                .isbn("2222222222")
                .publicationDate(LocalDate.now())
                .summary("This is a sample book.")
                .authorId(createdAuthor.getId())
                .build();

        String bookResponseAsString = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer newBookId = JsonPath.read(bookResponseAsString, "$.data.id");

        BookDTO updatedBookDTO = BookDTO.builder()
                .title("Updated Book")
                .isbn("3333333333")
                .publicationDate(LocalDate.now())
                .summary("This is an updated book.")
                .authorId(createdAuthor.getId())
                .build();

        String updatePayload = objectMapper.writeValueAsString(updatedBookDTO);

        mockMvc.perform(put("/books/{id}", newBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Book"))
                .andExpect(jsonPath("$.data.isbn").value("3333333333"))
                .andExpect(jsonPath("$.data.publicationDate").value(LocalDate.now().toString()));

        mockMvc.perform(delete("/books/{id}", newBookId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/{id}", newBookId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with ID: " + newBookId));
    }
}
