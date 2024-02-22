package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.dtos.AwardDto;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.repositories.AwardRepository;
import com.perficient.movies.repositories.MovieRepository;
import com.perficient.movies.services.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class AwardServiceImpl implements AwardService<Award> {

    private static final String ERROR = "Error";

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Award createAward(Award awardParam) {
        Award award = awardParam;

        awardRepository.save(award);

        return award;
    }

    @Override
    public Award findAward(Long id) {
        return awardRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.AWARD_NOT_FOUND));
    }

    @Override
    public Collection findAllAwards() {
        return (Collection<Award>) awardRepository.findAll();
    }

    @Override
    public void editAward(Long id, AwardDto awardDto) {

        Award awardById = awardRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.AWARD_NOT_FOUND));

        awardById.setName(awardDto.getName());
        awardById.setOrganization(awardDto.getOrganization());
        awardById.setYear(awardDto.getYear());

        awardRepository.save(awardById);
    }

    @Override
    public void deleteAward(Long id) {
        Award awardById = awardRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.AWARD_NOT_FOUND));

        awardRepository.delete(awardById);
    }

    @Override
    public void assignAward(Long awardId, Long movieId) {
        Award awardById = awardRepository.findById(awardId).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.AWARD_NOT_FOUND));

        Movie movieById = movieRepository.findById(movieId).orElseThrow((() -> new BusinessException(ERROR, ErrorCodesEnum.MOVIE_NOT_FOUND)));

        if (awardById.getMovie() != null)
            throw new BusinessException(ERROR, ErrorCodesEnum.AWARD_ALREADY_HAS_A_WINNER);

        awardById.setMovie(movieById);

        awardRepository.save(awardById);
    }

    @Override
    public Collection awardsFromMovie(Long movieId) {
        return awardRepository.getAwardsFromMovie(movieId);
    }
}
