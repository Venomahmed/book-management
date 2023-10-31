package com.ness.bookmanagement.bookmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@Rollback(true)
@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // You can set up any initial data or perform other setup here
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenAuthorDTO_withNoBooks_thenVerifySuccess() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstName("John");
        authorDTO.setLastName("Doe");
        authorDTO.setBiography("Author biography");

        String requestPayload = objectMapper.writeValueAsString(authorDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authors/")
                        .content(requestPayload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(mvcResult -> {
                    String jsonResponse = mvcResult.getResponse().getContentAsString();
                    AuthorDTO responseDTO = objectMapper.readValue(jsonResponse, AuthorDTO.class);

                    assertThat(responseDTO)
                            .extracting(AuthorDTO::getId, AuthorDTO::getFirstName, AuthorDTO::getLastName)
                            .containsExactly(1L, "John", "Doe");
                });

    }

    @Test
    public void testGetAuthorById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(mvcResult -> {
                    String jsonResponse = mvcResult.getResponse().getContentAsString();
                    AuthorDTO authorDTO = objectMapper.readValue(jsonResponse, AuthorDTO.class);

                    assertThat(authorDTO)
                            .extracting(AuthorDTO::getId, AuthorDTO::getFirstName, AuthorDTO::getLastName)
                            .containsExactly(1L, "John", "Doe");
                });
        ;
    }


}