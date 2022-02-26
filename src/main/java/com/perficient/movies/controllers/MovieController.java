package com.perficient.movies.controllers;

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
    public ResponseEntity<?> createMovie(@RequestBody MovieDto movieRequest) {

        movieService.createMovie(movieRequest.getName(), movieRequest.getDuration(),
                movieRequest.getPlot(), movieRequest.getReleaseDate());

        return new ResponseEntity<>("Movie created.", HttpStatus.CREATED);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<?> findMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(movieService.findMovie(movieId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{movie_id}")
    public ResponseEntity<?> editMovie(@PathVariable("movie_id") Long movieId, @RequestBody MovieDto movieDto) {
        movieService.editMovie(movieId, movieDto);
        return new ResponseEntity<>("Movie update.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{movie_id}")
    public ResponseEntity<?> removeMovie(@PathVariable("movie_id") Long movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>("Movie deleted.", HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> findAllMovies() {
        return new ResponseEntity<>(movieMapper.toDtoList((List<Movie>) movieService.findAllMovies()), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{movie_id}/add-actor/{actor_id}")
    public ResponseEntity<?> addActor(@PathVariable("movie_id") Long movieId, @PathVariable("actor_id") Long actorId) {
        movieService.addActor(movieId, actorId);
        return new ResponseEntity<>("Actor added.", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{movie_id}/add-director/{director_id}")
    public ResponseEntity<?> addDirector(@PathVariable("movie_id") Long movieId, @PathVariable("director_id") Long directorId) {
        movieService.addDirector(movieId, directorId);
        return new ResponseEntity<>("Director added.", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{movie_id}/remove-actor/{actor_id}")
    public ResponseEntity<?> removeActor(@PathVariable("movie_id") Long movieId, @PathVariable("actor_id") Long actorId) {
        movieService.removeActor(movieId, actorId);
        return new ResponseEntity<>("Actor has been removed.", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{movie_id}/remove-director/{director_id}")
    public ResponseEntity<?> removeDirector(@PathVariable("movie_id") Long movieId, @PathVariable("director_id") Long directorId) {
        movieService.removeDirector(movieId, directorId);
        return new ResponseEntity<>("Director has been removed.", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{movie_id}/actors")
    public ResponseEntity<?> actorsFromMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(actorMapper.toDtoList((List<Actor>) movieService.actorsFromMovie(movieId)), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{movie_id}/directors")
    public ResponseEntity<?> directorsFromMovie(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(directorMapper.toDtoList((List<Director>) movieService.findDirectorsFromMovie(movieId)), HttpStatus.ACCEPTED);
    }

}
