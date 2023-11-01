package com.ness.bookmanagement.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import com.ness.bookmanagement.bookmanagement.service.author.AuthorService;
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
public class AuthorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @Test
    public void givenAuthorDTO_thenVerifyCreateAndGetEndpoint() throws Exception {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .biography("Author biography")
                .dateOfBirth(LocalDate.now())
                .build();

        String payload = objectMapper.writeValueAsString(authorDTO);

        String authorResponseAsString = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.biography").value("Author biography"))
                .andExpect(jsonPath("$.data.dateOfBirth").value(LocalDate.now().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer newAuthorId = JsonPath.read(authorResponseAsString, "$.data.id");

        mockMvc.perform(get("/authors/{id}", newAuthorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(newAuthorId))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.biography").value("Author biography"))
                .andExpect(jsonPath("$.data.dateOfBirth").value(LocalDate.now().toString()));
    }

    @Test
    public void givenAuthorDTO_thenAssertUpdateAndDeleteEndpoint() throws Exception {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .biography("Author biography")
                .dateOfBirth(LocalDate.now())
                .build();

        String createPayload = objectMapper.writeValueAsString(authorDTO);

        String authorResponseAsString = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.biography").value("Author biography"))
                .andExpect(jsonPath("$.data.dateOfBirth").value(LocalDate.now().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer newAuthorId = JsonPath.read(authorResponseAsString, "$.data.id");

        AuthorDTO updateAuthorDTO = AuthorDTO.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .biography("Author biography Updated")
                .dateOfBirth(LocalDate.now())
                .build();
        String updatePayload = objectMapper.writeValueAsString(updateAuthorDTO);

        mockMvc.perform(put("/authors/{id}", newAuthorId)
                        .content(updatePayload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(newAuthorId))
                .andExpect(jsonPath("$.data.firstName").value(updateAuthorDTO.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(updateAuthorDTO.getLastName()))
                .andExpect(jsonPath("$.data.biography").value(updateAuthorDTO.getBiography()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(updateAuthorDTO.getDateOfBirth().toString()));

        mockMvc.perform(delete("/authors/{id}", newAuthorId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/authors/{id}", newAuthorId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Author not found with ID: " + newAuthorId));
    }


    @Test
    public void testGetBooksByAuthor_givenListOfBooks_thenAssertResponse() throws Exception {
        // GIVEN
        AuthorDTO authorDTO = AuthorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .biography("Author biography")
                .dateOfBirth(LocalDate.now())
                .build();
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);

        BookDTO bookDTO1 = BookDTO.builder()
                .title("Sample Book")
                .isbn("4444444444")
                .publicationDate(LocalDate.now())
                .summary("This is a sample book.")
                .authorId(createdAuthor.getId())
                .build();

        BookDTO bookDTO2 = BookDTO.builder()
                .title("Sample Book 2")
                .isbn("5555555555")
                .publicationDate(LocalDate.now())
                .summary("This is a sample book 2.")
                .authorId(createdAuthor.getId())
                .build();

        bookService.createBook(bookDTO1);
        bookService.createBook(bookDTO2);

        // WHEN & THEN
        mockMvc.perform(get("/authors/" + createdAuthor.getId() + "/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title").value(bookDTO1.getTitle()))
                .andExpect(jsonPath("$.data[0].isbn").value(bookDTO1.getIsbn()))
                .andExpect(jsonPath("$.data[0].publicationDate").value(bookDTO1.getPublicationDate().toString()))
                .andExpect(jsonPath("$.data[0].summary").value(bookDTO1.getSummary()))

                .andExpect(jsonPath("$.data[1].id").isNotEmpty())
                .andExpect(jsonPath("$.data[1].title").value(bookDTO2.getTitle()))
                .andExpect(jsonPath("$.data[1].isbn").value(bookDTO2.getIsbn()))
                .andExpect(jsonPath("$.data[1].publicationDate").value(bookDTO2.getPublicationDate().toString()))
                .andExpect(jsonPath("$.data[1].summary").value(bookDTO2.getSummary()));
    }
}
