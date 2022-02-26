package com.perficient.movies.services;

import com.perficient.movies.model.dtos.DirectorDto;
import java.util.Collection;

public interface DirectorService<T> {

    T createDirector(String name, String lastName, String bio, boolean isActive, String mainGender, boolean hasDirectedSeries);

    T findDirector(Long id);

    Collection<T> findAllDirectors();

    void editDirector(Long id, DirectorDto director);

    void deleteDirector(Long id);
}
