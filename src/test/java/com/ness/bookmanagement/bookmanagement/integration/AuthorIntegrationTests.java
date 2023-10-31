package com.ness.bookmanagement.bookmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.bookmanagement.bookmanagement.dto.ApiResponse;
import com.ness.bookmanagement.bookmanagement.dto.AuthorDTO;
import com.ness.bookmanagement.bookmanagement.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthorIntegrationTests {

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
                    ApiResponse<AuthorDTO> apiResponse = objectMapper.readValue(jsonResponse, ApiResponse.class);

                    assertThat(apiResponse)
                            .extracting("data")
                            .extracting("id", "firstName", "lastName")
                            .containsExactly(1, "John", "Doe");
                });

    }

}