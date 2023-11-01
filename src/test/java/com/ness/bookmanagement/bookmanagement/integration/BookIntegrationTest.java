package com.ness.bookmanagement.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.respository.AuthorEntityRepository;
import com.ness.bookmanagement.bookmanagement.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {
    private static final String BOOK_URL = "/v1/books";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorEntityRepository authorEntityRepository;
    @Autowired
    private BookService bookService;

    @Test
    public void givenBookDTO_withValidAuthorEntity_thenVerifyCreateAndGetEndpoint() throws Exception {
        final Long authorId = 1L;

        final BookDTO bookDTO = BookDTO.builder()
                .title("Web Development Basics")
                .isbn("978-0987654321")
                .publicationDate(LocalDate.parse("2021-12-03"))
                .summary("Cryptic codes and wartime intrigue.")
                .authorId(authorId)
                .build();

        String payload = objectMapper.writeValueAsString(bookDTO);

        String bookResponseAsString = mockMvc.perform(post(BOOK_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data.isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data.publicationDate").value(bookDTO.getPublicationDate().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer newBookId = JsonPath.read(bookResponseAsString, "$.data.id");

        mockMvc.perform(get(BOOK_URL + "/{id}", newBookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(newBookId))
                .andExpect(jsonPath("$.data.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data.isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data.publicationDate").value(bookDTO.getPublicationDate().toString()));
    }

    @Test
    public void givenBookDTO_withValidAuthorEntity_thenVerifyUpdateAndDeleteBook() throws Exception {
        final Long authorId = 4L;
        final Long bookId = 8L;

        final BookDTO bookDTO = BookDTO.builder()
                .title("Whispers of History Updated")
                .isbn("978-0743273566")
                .publicationDate(LocalDate.parse("2018-04-10"))
                .summary("An epic historical saga spanning generations. Updated")
                .authorId(authorId)
                .build();

        String updatePayload = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(put(BOOK_URL + "/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data.isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data.publicationDate").value(bookDTO.getPublicationDate().toString()));

        mockMvc.perform(delete(BOOK_URL + "/{id}", bookId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BOOK_URL + "/{id}", bookId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with ID: " + bookId));
    }

    @Test
    public void testGetBookDTO_whenIsbn_thenAssertResponse() throws Exception {
        // GIVEN
        final Long authorId = 2L;

        final BookDTO bookDTO = BookDTO.builder()
                .title("Love in Paris")
                .isbn("978-0062407689")
                .publicationDate(LocalDate.parse("2021-07-20"))
                .summary("A heartwarming love story set in the city of love.")
                .authorId(authorId)
                .build();

        final BookDTO bookDTO2 = BookDTO.builder()
                .title("Summer Love")
                .isbn("978-0345547976")
                .publicationDate(LocalDate.parse("2019-06-30"))
                .summary("A delightful summer romance that warms your heart.")
                .authorId(authorId)
                .build();

        final String firstName = "Jane";
        final String lastName = "Smith";


        // WHEN & THEN
        mockMvc.perform(get(BOOK_URL + "/by-author")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data[0].isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data[0].publicationDate").value(bookDTO.getPublicationDate().toString()))
                .andExpect(jsonPath("$.data[0].authorId").value(authorId))

                .andExpect(jsonPath("$.data[1].id").isNotEmpty())
                .andExpect(jsonPath("$.data[1].title").value(bookDTO2.getTitle()))
                .andExpect(jsonPath("$.data[1].isbn").value(bookDTO2.getIsbn()))
                .andExpect(jsonPath("$.data[1].publicationDate").value(bookDTO2.getPublicationDate().toString()))
                .andExpect(jsonPath("$.data[1].authorId").value(authorId));


        mockMvc.perform(get(BOOK_URL + "/by-author")
                        .param("firstName", firstName.toLowerCase())
                        .param("lastName", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data[0].isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data[0].publicationDate").value(bookDTO.getPublicationDate().toString()));

        mockMvc.perform(get(BOOK_URL + "/by-author")
                        .param("firstName", "")
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.data[0].isbn").value(bookDTO.getIsbn()))
                .andExpect(jsonPath("$.data[0].publicationDate").value(bookDTO.getPublicationDate().toString()));
    }
}
