package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Director;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DirectorRepository extends PagingAndSortingRepository<Director, Long> {
}
