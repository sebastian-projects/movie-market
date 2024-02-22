package com.perficient.movies.services;

import com.perficient.movies.model.dtos.ActorDto;
import com.perficient.movies.model.entities.Actor;

import java.util.Collection;

public interface ActorService<T> {

    T createActor(Actor actor);

    T findActor(Long id);

    Collection<T> findAllActors();

    void editActor(Long id, Actor actor);

    void deleteActor(Long id);

}
