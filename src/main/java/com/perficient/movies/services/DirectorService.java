package com.perficient.movies.services;

import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.entities.Director;

import java.util.Collection;

public interface DirectorService<T> {

    T createDirector(Director director);

    T findDirector(Long id);

    Collection<T> findAllDirectors();

    void editDirector(Long id, DirectorDto director);

    void deleteDirector(Long id);
}
