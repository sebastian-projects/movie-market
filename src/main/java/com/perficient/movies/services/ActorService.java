package com.perficient.movies.services;

import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(String name, String lastName, String bio, boolean isActive, String biggestRole, int startYear) {

        Actor actor = new Actor(name, lastName, bio, isActive, startYear, biggestRole);

        actorRepository.save(actor);

        return actor;
    }

    public Actor findActor(Long id) {

        Actor actorById = actorRepository.findById(id).orElseThrow(RuntimeException::new);

        return actorById;
    }

    public List<Actor> findAllActors() {

        Iterable<Actor> all = actorRepository.findAll();

        return (List<Actor>) all;
    }

    public Actor editActor(Long id, Actor actor) {

        Actor actorById = actorRepository.findById(id).orElseThrow(RuntimeException::new);

        actorById.setName(actor.getName());
        actorById.setLastName(actor.getLastName());
        actorById.setBio(actor.getBio());
        actorById.setIsActive(actor.getIsActive());
        actorById.setBiggestRole(actor.getBiggestRole());
        actorById.setStartYear(actor.getStartYear());

        actorRepository.save(actorById);

        return actorById;
    }

    public Actor deleteActor(Long id) {

        Actor actorById = actorRepository.findById(id).orElseThrow(RuntimeException::new);

        actorRepository.delete(actorById);

        return actorById;
    }

}
