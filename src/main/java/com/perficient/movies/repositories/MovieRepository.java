package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    @Query("SELECT actors FROM Movie movie WHERE movie.id = :movieId")
    List<Actor> getActorsFromMovie(Long movieId);

    @Query("SELECT directors FROM Movie movie WHERE movie.id = :movieId")
    List<Director> getDirectorsFromMovie(Long movieId);
}
