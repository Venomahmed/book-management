package com.ness.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ness.bookmanagement.config.JwtUtil;
import com.ness.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.service.author.AuthorService;
import com.ness.bookmanagement.service.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorIntegrationTest {
    private static final String AUTHOR_URL = "/v1/authors";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private JwtUtil jwtUtil;

    private String bearerToken;
    private final String USERNAME = "istiak";

    @BeforeEach
    void beforeEach() {
        String token = jwtUtil.generateToken(USERNAME);
        bearerToken = "Bearer " + token;
    }


    @Test
    public void testCreateAuthor_andGetAuthorById() throws Exception {
        // GIVEN
        final AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("Emily")
                .lastName("Johnson")
                .biography("Award-winning novelist and essayist.")
                .dateOfBirth(LocalDate.parse("1985-08-22"))
                .build();

        final String payload = objectMapper.writeValueAsString(authorDTO);

        // WHEN & THEN
        final String authorResponseAsString = mockMvc.perform(post(AUTHOR_URL)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value(authorDTO.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(authorDTO.getLastName()))
                .andExpect(jsonPath("$.data.biography").value(authorDTO.getBiography()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(authorDTO.getDateOfBirth().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Integer newAuthorId = JsonPath.read(authorResponseAsString, "$.data.id");

        mockMvc.perform(get(AUTHOR_URL + "/{id}", newAuthorId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(newAuthorId))
                .andExpect(jsonPath("$.data.firstName").value(authorDTO.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(authorDTO.getLastName()))
                .andExpect(jsonPath("$.data.biography").value(authorDTO.getBiography()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(authorDTO.getDateOfBirth().toString()));
    }

    @Test
    public void testUpdatedAuthor_andDeleteById() throws Exception {
        // GIVEN
        final Long authorId = 4L;

        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("Sophia Updated")
                .lastName("Lee Updated")
                .biography("An award-winning author famous for historical fiction. Updated")
                .dateOfBirth(LocalDate.parse("1988-11-30"))
                .build();

        String updatePayload = objectMapper.writeValueAsString(authorDTO);


        // WHEN & THEN
        mockMvc.perform(put(AUTHOR_URL + "/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .content(updatePayload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(authorId))
                .andExpect(jsonPath("$.data.firstName").value(authorDTO.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(authorDTO.getLastName()))
                .andExpect(jsonPath("$.data.biography").value(authorDTO.getBiography()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(authorDTO.getDateOfBirth().toString()));

        mockMvc.perform(delete(AUTHOR_URL + "/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(AUTHOR_URL + "/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Author not found with ID: " + authorId));
    }


    @Test
    public void testGetBooksByAuthor_givenListOfBooks_thenAssertResponse() throws Exception {
        // GIVEN
        final Long authorId = 2L;

        // WHEN & THEN
        mockMvc.perform(get(AUTHOR_URL + "/" + authorId + "/books")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Love in Paris"))
                .andExpect(jsonPath("$.data[0].isbn").value("978-0062407689"))
                .andExpect(jsonPath("$.data[0].publicationDate").value("2021-07-20"))
                .andExpect(jsonPath("$.data[0].summary").value("A heartwarming love story set in the city of love."))
                .andExpect(jsonPath("$.data[0].authorId").value(authorId))

                .andExpect(jsonPath("$.data[1].id").value(6))
                .andExpect(jsonPath("$.data[1].title").value("Summer Love"))
                .andExpect(jsonPath("$.data[1].isbn").value("978-0345547976"))
                .andExpect(jsonPath("$.data[1].publicationDate").value("2019-06-30"))
                .andExpect(jsonPath("$.data[1].summary").value("A delightful summer romance that warms your heart."))
                .andExpect(jsonPath("$.data[1].authorId").value(authorId));
    }
}
