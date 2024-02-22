package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.mappers.DirectorMapper;
import com.perficient.movies.repositories.DirectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DirectorServiceImplTest {

    @Autowired
    DirectorServiceImpl directorService;

    @Autowired
    DirectorMapper directorMapper;

    @MockBean
    DirectorRepository directorRepository;

    @Test
    void createDirectorTest() {

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.save(director)).thenReturn(director);

        directorService.createDirector(director);

        Mockito.verify(directorRepository, Mockito.times(1)).save(director);

    }

    @Test
    void findDirectorTest() {
        long directorId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        directorService.findDirector(directorId);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);
    }

    @Test
    void findDirectorNotFoundTest() {
        long directorId = 1L;

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> directorService.findDirector(directorId));

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.DIRECTOR_NOT_FOUND, exception.getCode());
    }

    @Test
    void findAllDirectorsTest() {
        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        List<Director> directors = List.of(director, director, director);

        Mockito.when(directorRepository.findAll()).thenReturn(directors);

        List<Director> directorsFind = (List<Director>) directorService.findAllDirectors();

        Mockito.verify(directorRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(3, directorsFind.size());
    }

    @Test
    void editDirectorTest() {
        Long directorId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.findById(1L)).thenReturn(Optional.of(director));

        Mockito.when(directorRepository.save(director)).thenReturn(director);

        directorService.editDirector(directorId, directorMapper.toDto(director));

        Mockito.verify(directorRepository, Mockito.times(1)).save(director);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    void editDirectorNotFoundTest() {
        Long directorId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> directorService.editDirector(directorId, directorMapper.toDto(director)));

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.DIRECTOR_NOT_FOUND, exception.getCode());

        Mockito.verify(directorRepository, Mockito.times(0)).save(director);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    void deleteDirectorTest() {
        Long directorId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.findById(1L)).thenReturn(Optional.of(director));

        Mockito.doNothing().when(directorRepository).delete(director);

        directorService.deleteDirector(directorId);

        Mockito.verify(directorRepository, Mockito.times(1)).delete(director);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);
    }

    @Test
    void deleteDirectorNotFoundTest() {
        Long directorId = 1L;

        Director director = new Director("George", "Lucas", "bio", true, "Sci-Fi", true);

        Mockito.when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> directorService.deleteDirector(directorId));

        Mockito.verify(directorRepository, Mockito.times(1)).findById(directorId);

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.DIRECTOR_NOT_FOUND, exception.getCode());

        Mockito.verify(directorRepository, Mockito.times(0)).delete(director);

        Mockito.verify(directorRepository, Mockito.times(1)).findById(1L);

    }
}