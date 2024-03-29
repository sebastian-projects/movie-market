package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.dtos.MovieDto;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.repositories.ActorRepository;
import com.perficient.movies.repositories.DirectorRepository;
import com.perficient.movies.repositories.MovieRepository;
import com.perficient.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Date;


@Service
@Transactional
public class MovieServiceImpl implements MovieService<Movie> {

    private static final String ERROR = "Error";

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Movie createMovie(Movie movieParam) {

        Movie movie = movieParam;

        movieRepository.save(movie);

        return movie;
    }

    @Override
    public Movie findMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));
    }

    @Override
    public Collection findAllMovies() {
        return (Collection<Movie>) movieRepository.findAll();
    }

    @Override
    public void editMovie(Long id, MovieDto movie) {

        Movie movieById = movieRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        movieById.setName(movie.getName());
        movieById.setPlot(movie.getPlot());
        movieById.setDuration(movie.getDuration());
        movieById.setReleaseDate(movie.getReleaseDate());

        movieRepository.save(movieById);
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movieById = movieRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        movieRepository.delete(movieById);
    }

    @Override
    public void addActor(Long movieId, Long actorId) {
        Movie movieById = movieRepository.findById(movieId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        Actor actorById = actorRepository.findById(actorId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.ACTOR_NOT_FOUND));

        movieById.getActors().add(actorById);

        movieRepository.save(movieById);
    }

    @Override
    public void addDirector(Long movieId, Long directorId) {
        Movie movieById = movieRepository.findById(movieId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        Director directorById = directorRepository.findById(directorId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND));

        movieById.getDirectors().add(directorById);

        movieRepository.save(movieById);
    }

    @Override
    public void removeActor(Long movieId, Long actorId) {
        Movie movieById = movieRepository.findById(movieId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        Actor actorById = actorRepository.findById(actorId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.ACTOR_NOT_FOUND));

        if (!movieById.getActors().contains(actorById))
            throw new BusinessException(ERROR, ErrorCodesEnum.ACTOR_NOT_FOUND_IN_MOVIE);

        movieById.getActors().remove(actorById);

        movieRepository.save(movieById);
    }

    @Override
    public void removeDirector(Long movieId, Long directorId) {
        Movie movieById = movieRepository.findById(movieId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND));

        Director directorById = directorRepository.findById(directorId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND));

        if (!movieById.getDirectors().contains(directorById))
            throw new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND_IN_MOVIE);

        movieById.getDirectors().remove(directorById);

        movieRepository.save(movieById);
    }

    @Override
    public Collection actorsFromMovie(Long movieId) {
        return movieRepository.getActorsFromMovie(movieId);
    }

    @Override
    public Collection findDirectorsFromMovie(Long movieId) {
        return movieRepository.getDirectorsFromMovie(movieId);
    }

}
