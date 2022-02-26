package com.perficient.movies.services;

import com.perficient.movies.model.dtos.MovieDto;

import java.util.Collection;
import java.util.Date;

public interface MovieService<T> {

    T createMovie(String name, int duration, String plot, Date releaseDate);

    T findMovie(Long id);

    Collection<T> findAllMovies();

    void editMovie(Long id, MovieDto movie);

    void deleteMovie(Long id);

    void addActor(Long movieId, Long actorId);

    void addDirector(Long movieId, Long directorId);

    void removeActor(Long movieId, Long actorId);

    void removeDirector(Long movieId, Long directorId);

    Collection<T> actorsFromMovie(Long movieId);

    Collection<T> findDirectorsFromMovie(Long movieId);
}
