package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.repositories.DirectorRepository;
import com.perficient.movies.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class DirectorServiceImpl implements DirectorService<Director> {

    private static final String ERROR = "Error";

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Director createDirector(Director directorParam) {

        Director director = directorParam;

        directorRepository.save(director);

        return director;
    }

    @Override
    public Director findDirector(Long id) {
        return directorRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND));
    }

    @Override
    public Collection<Director> findAllDirectors() {
        return (Collection<Director>) directorRepository.findAll();
    }

    @Override
    public void editDirector(Long id, DirectorDto director) {

        Director directorById = directorRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND));

        directorById.setName(director.getName());
        directorById.setLastName(director.getLastName());
        directorById.setBio(director.getBio());
        directorById.setActive(director.isActive());
        directorById.setMainGender(director.getMainGender());
        directorById.setHasDirectedSeries(director.isHasDirectedSeries());

        directorRepository.save(directorById);
    }

    @Override
    public void deleteDirector(Long id) {
        Director directorById = directorRepository.findById(id).orElseThrow(() -> new BusinessException(ERROR, ErrorCodesEnum.DIRECTOR_NOT_FOUND));

        directorRepository.delete(directorById);
    }
}
