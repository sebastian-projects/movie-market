package com.perficient.movies.controllers;

import com.perficient.movies.model.dtos.ActorDto;
import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.dtos.MovieDto;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.model.mappers.ActorMapper;
import com.perficient.movies.model.mappers.DirectorMapper;
import com.perficient.movies.model.mappers.MovieMapper;
import com.perficient.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private DirectorMapper directorMapper;

    @PostMapping
    public ResponseEntity<String> createMovie(@RequestBody MovieDto movieRequest) {

        movieService.createMovie(movieMapper.toEntity(movieRequest));

        return new ResponseEntity<>("Movie created.", HttpStatus.CREATED);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<MovieDto> findMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(movieMapper.toDto((Movie) movieService.findMovie(movieId)), HttpStatus.OK);
    }

    @PutMapping("/{movie_id}")
    public ResponseEntity<String> editMovie(@PathVariable("movie_id") Long movieId, @RequestBody MovieDto movieDto) {
        movieService.editMovie(movieId, movieDto);
        return new ResponseEntity<>("Movie update.", HttpStatus.OK);
    }

    @DeleteMapping("/{movie_id}")
    public ResponseEntity<String> removeMovie(@PathVariable("movie_id") Long movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>("Movie deleted.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> findAllMovies() {
        return new ResponseEntity<>(movieMapper.toDtoList((List<Movie>) movieService.findAllMovies()), HttpStatus.OK);
    }

    @PatchMapping("/{movie_id}/actors/{actor_id}")
    public ResponseEntity<String> addActor(@PathVariable("movie_id") Long movieId, @PathVariable("actor_id") Long actorId) {
        movieService.addActor(movieId, actorId);
        return new ResponseEntity<>("Actor added.", HttpStatus.OK);
    }

    @PatchMapping("/{movie_id}/directors/{director_id}")
    public ResponseEntity<String> addDirector(@PathVariable("movie_id") Long movieId, @PathVariable("director_id") Long directorId) {
        movieService.addDirector(movieId, directorId);
        return new ResponseEntity<>("Director added.", HttpStatus.OK);
    }

    @DeleteMapping("/{movie_id}/actors/{actor_id}")
    public ResponseEntity<String> removeActor(@PathVariable("movie_id") Long movieId, @PathVariable("actor_id") Long actorId) {
        movieService.removeActor(movieId, actorId);
        return new ResponseEntity<>("Actor has been removed.", HttpStatus.OK);
    }

    @DeleteMapping("/{movie_id}/directors/{director_id}")
    public ResponseEntity<String> removeDirector(@PathVariable("movie_id") Long movieId, @PathVariable("director_id") Long directorId) {
        movieService.removeDirector(movieId, directorId);
        return new ResponseEntity<>("Director has been removed.", HttpStatus.OK);
    }

    @GetMapping("/{movie_id}/actors")
    public ResponseEntity<List<ActorDto>> actorsFromMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(actorMapper.toDtoList((List<Actor>) movieService.actorsFromMovie(movieId)), HttpStatus.OK);
    }

    @GetMapping("/{movie_id}/directors")
    public ResponseEntity<List<DirectorDto>> directorsFromMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(directorMapper.toDtoList((List<Director>) movieService.findDirectorsFromMovie(movieId)), HttpStatus.OK);
    }

}
