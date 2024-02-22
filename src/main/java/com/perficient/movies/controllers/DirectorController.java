package com.perficient.movies.controllers;

import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.entities.Director;
import com.perficient.movies.model.mappers.DirectorMapper;
import com.perficient.movies.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    @Autowired
    private DirectorService<Director> directorService;

    @Autowired
    private DirectorMapper directorMapper;

    @PostMapping
    public ResponseEntity<String> createDirector(@RequestBody Director director) {

        directorService.createDirector(director);

        return new ResponseEntity<>("Director created.", HttpStatus.CREATED);
    }

    @GetMapping("/{director_id}")
    public ResponseEntity<DirectorDto> findDirector(@PathVariable("director_id") Long directorId) {
        return new ResponseEntity<>(directorMapper.toDto(directorService.findDirector(directorId)), HttpStatus.OK);
    }

    @PutMapping("/{director_id}")
    public ResponseEntity<String> updateDirector(@PathVariable("director_id") Long directorId, @RequestBody DirectorDto directorRequest) {
        directorService.editDirector(directorId, directorRequest);
        return new ResponseEntity<>("Director update.", HttpStatus.OK);
    }

    @DeleteMapping("/{director_id}")
    public ResponseEntity<String> deleteDirector(@PathVariable("director_id") Long directorId) {
        directorService.deleteDirector(directorId);
        return new ResponseEntity<>("Director deleted.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DirectorDto>> findAllDirectors() {
        return new ResponseEntity<>(directorMapper.toDtoList((List<Director>) directorService.findAllDirectors()), HttpStatus.OK);
    }

}
