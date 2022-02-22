package com.perficient.movies.services;

import com.perficient.movies.model.dtos.ActorDTO;
import com.perficient.movies.model.entities.Actor;

import java.util.Collection;

public interface ActorService<T> {

    T createActor(String name, String lastName, String bio, boolean isActive, String biggestRole, int startYear);

    T findActor(Long id);

    Collection<T> findAllActors();

    void editActor(Long id, Actor actor);

    void deleteActor(Long id);

}
