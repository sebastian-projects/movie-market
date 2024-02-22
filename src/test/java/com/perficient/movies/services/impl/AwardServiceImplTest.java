package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.entities.Movie;
import com.perficient.movies.model.mappers.AwardMapper;
import com.perficient.movies.repositories.AwardRepository;
import com.perficient.movies.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
class AwardServiceImplTest {

    @Autowired
    AwardServiceImpl awardService;

    @Autowired
    AwardMapper awardMapper;

    @MockBean
    AwardRepository awardRepository;

    @MockBean
    MovieRepository movieRepository;

    @Test
    void createAwardTest() {
        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.save(award)).thenReturn(award);

        awardService.createAward(award);

        verify(awardRepository, times(1)).save(award);
    }

    @Test
    void findAwardTest() {
        long awardId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        awardService.findAward(awardId);

        verify(awardRepository, times(1)).findById(awardId);

    }

    @Test
    void findAwardNotFoundTest() {
        long awardId = 1L;

        when(awardRepository.findById(awardId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.findAward(awardId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.AWARD_NOT_FOUND, exception.getCode());

    }

    @Test
    void findAllAwardsTest() {
        Award award = new Award("Best Actor", "Oscars", 2019);

        List<Award> awards = List.of(award, award, award);

        when(awardRepository.findAll()).thenReturn(awards);

        awardService.findAllAwards();

        verify(awardRepository, times(1)).findAll();
    }

    @Test
    void editAwardTest() {
        long awardId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        when(awardRepository.save(award)).thenReturn(award);

        awardService.editAward(awardId, awardMapper.toDto(award));

        verify(awardRepository, times(1)).findById(awardId);

        verify(awardRepository, times(1)).save(award);
    }

    @Test
    void editAwardNotFound() {
        long awardId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.editAward(awardId, awardMapper.toDto(award)));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.AWARD_NOT_FOUND, exception.getCode());

        verify(awardRepository, times(1)).findById(awardId);

        verify(awardRepository, times(0)).save(award);
    }

    @Test
    void deleteAwardTest() {
        long awardId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        doNothing().when(awardRepository).delete(award);

        awardService.deleteAward(awardId);

        verify(awardRepository, times(1)).delete(award);
    }

    @Test
    void deleteAwardNotFound() {
        long awardId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.deleteAward(awardId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.AWARD_NOT_FOUND, exception.getCode());

        verify(awardRepository, times(1)).findById(awardId);

        verify(awardRepository, times(0)).delete(award);
    }

    @Test
    void assignAwardTest() {
        long movieId = 1L;

        long awardId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Award award = new Award("Best Movie", "Oscars", 2019);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        when(awardRepository.save(award)).thenReturn(award);

        awardService.assignAward(awardId, movieId);

        verify(awardRepository, times(1)).findById(awardId);

        verify(movieRepository, times(1)).findById(movieId);

        verify(awardRepository, times(1)).save(award);
    }

    @Test
    void assignAwardNotFoundTest() {
        long awardId = 1L;

        long movieId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.assignAward(awardId, movieId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.AWARD_NOT_FOUND, exception.getCode());

        verify(awardRepository, times(1)).findById(awardId);

        verify(awardRepository, times(0)).save(award);
    }

    @Test
    void assignAwardMovieNotFoundTest() {
        long awardId = 1L;

        long movieId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.assignAward(awardId, movieId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.MOVIE_NOT_FOUND, exception.getCode());

        verify(awardRepository, times(1)).findById(awardId);

        verify(movieRepository, times(1)).findById(awardId);

        verify(awardRepository, times(0)).save(award);
    }

    @Test
    void assignAwardWithWinner() {
        long awardId = 1L;

        long movieId = 1L;

        Award award = new Award("Best Actor", "Oscars", 2019);

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        award.setMovie(movie);

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> awardService.assignAward(awardId, movieId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.AWARD_ALREADY_HAS_A_WINNER, exception.getCode());

        verify(awardRepository, times(1)).findById(awardId);

        verify(movieRepository, times(1)).findById(awardId);

        verify(awardRepository, times(0)).save(award);
    }

    @Test
    void awardsFromMovieTest() {
        long movieId = 1L;

        Movie movie = new Movie("Parasite", 160, "Korean movie", new Date());

        Award award1 = new Award("Best Movie", "Oscars", 2019);

        Award award2 = new Award("Best Actor", "Oscars", 2019);

        Award award3 = new Award("Best Soundtrack", "Oscars", 2019);

        award1.setMovie(movie);

        award2.setMovie(movie);

        award3.setMovie(movie);

        List<Award> awards = List.of(award1, award2, award3);

        when(awardRepository.getAwardsFromMovie(movieId)).thenReturn(awards);

        List<Award> awardsByMovie = (List<Award>) awardService.awardsFromMovie(movieId);

        verify(awardRepository, times(1)).getAwardsFromMovie(movieId);

        Assertions.assertEquals(3, awardsByMovie.size());
    }
}