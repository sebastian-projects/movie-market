package com.perficient.movies.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.exceptions.ErrorResponse;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.services.ActorService;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ActorService actorService;

    @Test
    void createActor() throws Exception {

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        String json = objectMapper.writeValueAsString(actor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/actors")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.CREATED.value(), status);

        assertEquals("Actor created.", body);
    }

    @Test
    void createActorDataIncomplete() throws Exception {

        Actor actor = new Actor("", "Driver", "bio", true, 2010, "Kylo Ren");

        String json = objectMapper.writeValueAsString(actor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/actors")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), status);

        assertEquals("DATA_INCOMPLETE", errorResponse.getMessage());

        assertEquals(ErrorCodesEnum.DATA_INCOMPLETE.getCode(), errorResponse.getErrorCode());
    }

    @Test
    void findActor() throws Exception {

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        Mockito.when(actorService.findActor(1L)).thenReturn(actor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/actors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        Actor actorResponse = objectMapper.readValue(body, Actor.class);

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(actor.getName(), actorResponse.getName());
    }

    @Test
    void findActorNotFound() throws Exception {

        Mockito.when(actorService.findActor(1L)).thenThrow(new BusinessException("Error", ErrorCodesEnum.ACTOR_NOT_FOUND));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/actors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), status);

        assertEquals("ACTOR_NOT_FOUND", errorResponse.getMessage());

        assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND.getCode(), errorResponse.getErrorCode());
    }

    @Test
    void updateActor() throws Exception {

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        String json = objectMapper.writeValueAsString(actor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/actors/1")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Actor update.", body);

    }

    @Test
    void updateActorDataIncomplete() throws Exception {

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        //Mockito.when(actorService.editActor(1L, actor)).thenThrow(new BusinessException("Error", ErrorCodesEnum.DATA_INCOMPLETE));

        //Mockito.when(actorService.editActor(1L, actor)).thenThrow(new BusinessException("Error", ErrorCodesEnum.DATA_INCOMPLETE));

        //Mockito.doThrow(new BusinessException("Error", ErrorCodesEnum.ACTOR_NOT_FOUND)).when(actorService).editActor(1L, actor);

        String json = objectMapper.writeValueAsString(actor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/actors/1")
                .contentType("application/json")
                .content(json);


        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), status);

        assertEquals("DATA_INCOMPLETE", errorResponse.getMessage());

        assertEquals(ErrorCodesEnum.DATA_INCOMPLETE.getCode(), errorResponse.getErrorCode());

    }

    @Test
    void deleteActor() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/actors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals("Actor deleted.", body);

        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void findAllActors() throws Exception {
        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        List<Actor> actors = List.of(actor);

        Mockito.when(actorService.findAllActors()).thenReturn(actors);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/actors");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Actor> actorsResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(1, actorsResponse.size());
    }
}