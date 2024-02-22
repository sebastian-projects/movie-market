package com.perficient.movies.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.services.DirectorService;
import com.perficient.movies.services.impl.DirectorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DirectorService directorService;

    @Test
    void createDirectorTest() throws Exception {

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        String json = objectMapper.writeValueAsString(director);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/directors")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.CREATED.value(), status);

        assertEquals("Director created.", body);
    }

    @Test
    void findDirectorTest() throws Exception {

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorService.findDirector(1L)).thenReturn(director);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/directors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        Director directorResponse = objectMapper.readValue(body, Director.class);

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(director.getName(), directorResponse.getName());
    }

    @Test
    void updateDirectorTest() throws Exception {
        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        String json = objectMapper.writeValueAsString(director);

        RequestBuilder requestBuilder =MockMvcRequestBuilders.put("/api/directors/1")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Director update.", body);
    }

    @Test
    void deleteDirectorTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/directors/1");

        ResultActions resultActions =mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals("Director deleted.", body);

        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void findAllDirectorsTest() throws Exception {
        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        List<Director> directors = List.of(director);

        Mockito.when(directorService.findAllDirectors()).thenReturn(directors);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/directors");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Director> directorsResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(1, directorsResponse.size());
    }
}