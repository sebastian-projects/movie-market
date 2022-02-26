package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Actor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {
}
