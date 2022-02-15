package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
