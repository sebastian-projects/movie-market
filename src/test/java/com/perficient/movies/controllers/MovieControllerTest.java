package com.perficient.movies.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.services.MovieService;
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
@AutoConfigureMockMvc
@Transactional
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @Test
    void createMovieTest() throws Exception {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        String json = objectMapper.writeValueAsString(movie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/movies")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.CREATED.value(), status);

        assertEquals("Movie created.", body);
    }

    @Test
    void findMovieTest() throws Exception {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieService.findMovie(1L)).thenReturn(movie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        Movie movieResponse = objectMapper.readValue(body, Movie.class);

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(movie.getName(), movieResponse.getName());
    }

    @Test
    void editMovieTest() throws Exception {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        String json = objectMapper.writeValueAsString(movie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/movies/1")
                .contentType("application/json")
                .content(json);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Movie update.", body);
    }

    @Test
    void removeMovieTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/movies/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals("Movie deleted.", body);

        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void findAllMoviesTest() throws Exception {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        List<Movie> movies = List.of(movie);

        Mockito.when(movieService.findAllMovies()).thenReturn(movies);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Movie> moviesResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(1, moviesResponse.size());
    }

    @Test
    void addActorTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/movies/1/actors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Actor added.", body);
    }

    @Test
    void addDirectorTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/movies/1/directors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Director added.", body);
    }

    @Test
    void removeActorTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/movies/1/actors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Actor has been removed.", body);
    }

    @Test
    void removeDirectorTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/movies/1/directors/1");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("Director has been removed.", body);
    }

    @Test
    void actorsFromMovieTest() throws Exception {
        Actor actor1 = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");
        Actor actor2 = new Actor("Sebastian", "Obando", "bio", true, 2002, "Yoda");

        List<Actor> actors = List.of(actor1, actor2);

        Mockito.when(movieService.actorsFromMovie(1L)).thenReturn(actors);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies/1/actors");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Actor> actorsResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(2, actorsResponse.size());
    }

    @Test
    void directorsFromMovieTest() throws Exception {
        Director director1 = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Director director2 = new Director("Sebastian", "Obando", "bio", true, "Sci-Fi", true);

        List<Director> directors = List.of(director1, director2);

        Mockito.when(movieService.findDirectorsFromMovie(1l)).thenReturn(directors);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies/1/directors");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        String body = resultActions.andReturn().getResponse().getContentAsString();

        List<Director> directorsResponse = objectMapper.readValue(body, List.class);

        int status = resultActions.andReturn().getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals(2, directorsResponse.size());
    }
}