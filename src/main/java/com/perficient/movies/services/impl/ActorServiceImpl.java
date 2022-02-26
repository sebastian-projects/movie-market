package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.repositories.ActorRepository;
import com.perficient.movies.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Actor createActor(String name, String lastName, String bio, boolean isActive, String biggestRole, int startYear) {
        Actor actor = new Actor(name, lastName, bio, isActive, startYear, biggestRole);

        actorRepository.save(actor);

        return actor;
    }

    @Override
    public Actor findActor(Long id) {
        Actor actorById = actorRepository.findById(id).orElseThrow(() -> new BusinessException("Error", ErrorCodesEnum.ACTOR_NOT_FOUND));

        return actorById;
    }

    @Override
    public Collection<Actor> findAllActors() {
        return (Collection<Actor>) actorRepository.findAll();
    }

    @Override
    public void editActor(Long id, Actor actor) {
        Actor actorById = actorRepository.findById(id).orElseThrow(() -> new BusinessException("Error", ErrorCodesEnum.ACTOR_NOT_FOUND));

        actorById.setName(actor.getName());
        actorById.setLastName(actor.getLastName());
        actorById.setBio(actor.getBio());
        actorById.setActive(actor.isActive());
        actorById.setBiggestRole(actor.getBiggestRole());
        actorById.setStartYear(actor.getStartYear());

        actorRepository.save(actorById);
    }

    @Override
    public void deleteActor(Long id) {
        Actor actorById = actorRepository.findById(id).orElseThrow(() -> new BusinessException("Error", ErrorCodesEnum.ACTOR_NOT_FOUND));

        actorRepository.delete(actorById);
    }

}
