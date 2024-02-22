package com.perficient.movies.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.movies.model.dtos.AwardDto;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.services.AwardService;
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

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AwardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AwardService awardService;

    @Test
    void createAwardTest() throws Exception {
        Award award = new Award("Best Actor", "Oscars", 2019);

        String json = objectMapper.writeValueAsString(award);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/awards")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.CREATED.value(), status);

        assertEquals("Award created.", body);
    }

    @Test
    void findAwardTest() throws Exception {
        Award award = new Award("Best Actor", "Oscars", 2019);

        Mockito.when(awardService.findAward(1L)).thenReturn(award);

        RequestBuilder  requestBuilder = MockMvcRequestBuilders.get("/api/awards/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        Award awardResponse = objectMapper.readValue(body, Award.class);

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(award.getName(), awardResponse.getName());
    }

    @Test
    void updateAwardTest() throws Exception {

        AwardDto award = new AwardDto("Best Actor", "Oscars", 2019);

        String json = objectMapper.writeValueAsString(award);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/awards/1")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Award update.", body);

    }

    @Test
    void deleteAwardTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/awards/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals("Award deleted.", body);

        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void findAllAwardsTest() throws Exception {
        Award award = new Award("Best Actor", "Oscars", 2019);

        List<Award> awards = List.of(award);

        Mockito.when(awardService.findAllAwards()).thenReturn(awards);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/awards");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Award> awardsResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(1, awardsResponse.size());
    }

    @Test
    void addWinnerTest() throws Exception {
        Award award = new Award("Best Actor", "Oscars", 2019);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/awards/1/winner/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Award assigned.", body);
    }

    @Test
    void getMovieAwardsTest() throws Exception {
        Award award1 = new Award("Best Actor", "Oscars", 2019);
        Award award2 = new Award("Best Movie", "Oscars", 2019);

        List<Award> awards = List.of(award1, award2);

        Mockito.when(awardService.awardsFromMovie(1L)).thenReturn(awards);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/awards/movies/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<AwardDto> awardsResponse = objectMapper.readValue(body, List.class);

        int status =  resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(2, awardsResponse.size());
    }
}