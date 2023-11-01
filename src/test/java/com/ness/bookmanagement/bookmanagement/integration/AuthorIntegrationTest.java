package com.ness.bookmanagement.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
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
}
