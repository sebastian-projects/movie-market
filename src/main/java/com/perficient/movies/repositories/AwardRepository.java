package com.perficient.movies.repositories;

import com.perficient.movies.model.entities.Award;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AwardRepository extends PagingAndSortingRepository<Award, Long> {
    @Query("SELECT award FROM Award award WHERE award.movie.id = :movieId")
    List<Award> getAwardsFromMovie(Long movieId);
}
