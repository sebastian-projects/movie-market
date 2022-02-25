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
    private DirectorService directorService;

    @Autowired
    private DirectorMapper directorMapper;

    @PostMapping
    public ResponseEntity<?> createDirector(@RequestBody DirectorDto directorRequest) {

        directorService.createDirector(directorRequest.getName(), directorRequest.getLastName(),
                directorRequest.getBio(), directorRequest.isActive(), directorRequest.getMainGender(),
                directorRequest.isHasDirectedSeries());

        return new ResponseEntity<>("Director created.", HttpStatus.CREATED);
    }

    @GetMapping("/{director_id}")
    public ResponseEntity<?> findDirector(@PathVariable("director_id") Long directorId) {
        return new ResponseEntity<>(directorMapper.toDto((Director) directorService.findDirector(directorId)), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{director_id}")
    public ResponseEntity<?> updateDirector(@PathVariable("director_id") Long directorId, @RequestBody DirectorDto directorRequest) {
        directorService.editDirector(directorId, directorRequest);
        return new ResponseEntity<>("Director update.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{director_id}")
    public ResponseEntity<?> deleteDirector(@PathVariable("director_id") Long directorId) {
        directorService.deleteDirector(directorId);
        return new ResponseEntity<>("Director deleted.", HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> findAllDirectors() {
        return new ResponseEntity<>(directorMapper.toDtoList((List<Director>) directorService.findAllDirectors()), HttpStatus.ACCEPTED);
    }

}
