package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Director;
import org.springframework.data.repository.CrudRepository;

public interface DirectorRepository extends CrudRepository<Director, Long> {
}
