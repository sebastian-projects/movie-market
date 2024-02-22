package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.model.mappers.MovieMapper;
import com.perficient.movies.repositories.ActorRepository;
import com.perficient.movies.repositories.DirectorRepository;
import com.perficient.movies.repositories.MovieRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MovieServiceImplTest {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MovieMapper movieMapper;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @Test
    void createMovieTest() {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        movieService.createMovie(movie);

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void findMovieTest() {
        long movieId = 1L;

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(new Movie("Parasite", 160, "Korean movie", new Date())));

        movieService.findMovie(movieId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
    }

    @Test
    void findMovieNotFoundTest() {
        long movieId = 1L;

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.findMovie(movieId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
    }

    @Test
    void findAllMoviesTest() {
        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        List<Movie> movies = List.of(movie, movie, movie);

        Mockito.when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> moviesFind = (List<Movie>) movieService.findAllMovies();

        Mockito.verify(movieRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(3, moviesFind.size());
    }

    @Test
    void editMovieTest() {
        long movieId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        movieService.editMovie(movieId, movieMapper.toDto(movie));

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
    }

    @Test
    void editMovieNotFoundTest() {
        long movieId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.editMovie(movieId, movieMapper.toDto(movie)));

        Assertions.assertEquals("Error", exception.getMessage());

        Assert.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
    }

    @Test
    void deleteMovieTest() {
        long movieId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(movieId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(movieRepository, Mockito.times(1)).delete(movie);
    }

    @Test
    void deleteMovieNotFoundTest() {
        long movieId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.deleteMovie(movieId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(movieRepository, Mockito.times(0)).delete(movie);
    }

    @Test
    void addActorTest() {
        long movieId = 1L;

        long actorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        movieService.addActor(movieId, actorId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(actorRepository, Mockito.times(1)).findById(actorId);

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void addActorMovieNotFoundTest() {
        long movieId = 1L;

        long actorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.addActor(movieId, actorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
    }

    @Test
    void addActorNotFoundTest() {
        long movieId = 1L;

        long actorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(actorRepository.findById(actorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.addActor(movieId, actorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(actorRepository, Mockito.times(1)).findById(actorId);

        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
    }

    @Test
    void addDirectorTest() {
        long movieId = 1L;

        long directorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        movieService.addDirector(movieId, directorId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void addDirectorMovieNotFoundTest() {
        long movieId = 1L;

        long directorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.addDirector(movieId, directorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
    }

    @Test
    void addDirectorNotFoundTest() {
        long movieId = 1L;

        long directorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.addDirector(movieId, directorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.DIRECTOR_NOT_FOUND, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
    }

    @Test
    void removeActorTest() {
        long movieId = 1L;

        long actorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        movie.getActors().add(actor);

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        movieService.removeActor(movieId, actorId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(actorRepository, Mockito.times(1)).findById(actorId);

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);

    }

    @Test
    void removeActorNotFoundInMovieTest() {
        long movieId = 1L;

        long actorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.removeActor(movieId, actorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND_IN_MOVIE, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(actorRepository, Mockito.times(1)).findById(actorId);

    }

    @Test
    void removeDirectorTest() {
        long movieId = 1L;

        long directorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        movie.getDirectors().add(director);

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        movieService.removeDirector(movieId, directorId);

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void removeDirectorNotFoundInMovieTest() {
        long movieId = 1L;

        long directorId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> movieService.removeDirector(movieId, directorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.DIRECTOR_NOT_FOUND_IN_MOVIE, exception.getCode());

        Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

    }

    @Test
    void actorsFromMovieTest() {
        long movieId = 1L;

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        List<Actor> actors = List.of(actor, actor, actor);

        Mockito.when(movieRepository.getActorsFromMovie(movieId)).thenReturn(actors);

        movieService.actorsFromMovie(movieId);

        Mockito.verify(movieRepository, Mockito.times(1)).getActorsFromMovie(movieId);
    }

    @Test
    void findDirectorsFromMovieTest() {
        long movieId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        List<Director> directors = List.of(director, director, director);

        Mockito.when(movieRepository.getDirectorsFromMovie(movieId)).thenReturn(directors);

        movieService.findDirectorsFromMovie(movieId);

        Mockito.verify(movieRepository, Mockito.times(1)).getDirectorsFromMovie(movieId);
    }
}