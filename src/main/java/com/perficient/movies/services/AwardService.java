package com.perficient.movies.services;

import com.perficient.movies.model.dtos.AwardDto;

import java.util.Collection;

public interface AwardService<T> {

    T createAward(String name, String organization, int year);

    T findAward(Long id);

    Collection<T> findAllAwards();

    void editAward(Long id, AwardDto awardDto);

    void deleteAward(Long id);

    void assignAward(Long awardId, Long movieId);

    Collection<T> awardsFromMovie(Long movieId);
}
