package com.perficient.movies.controllers;

import com.perficient.movies.model.dtos.AwardDto;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.mappers.AwardMapper;
import com.perficient.movies.services.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @Autowired
    private AwardMapper awardMapper;

    @PostMapping
    public ResponseEntity<?> createAward(@RequestBody AwardDto awardRequest) {

        awardService.createAward(awardRequest.getName(), awardRequest.getOrganization(),
                awardRequest.getYear());

        return new ResponseEntity<>("Award created.", HttpStatus.CREATED);
    }

    @GetMapping("/{award_id}")
    public ResponseEntity<?> findAward(@PathVariable("award_id") Long awardId) {
        return new ResponseEntity<>(awardMapper.toDto((Award) awardService.findAward(awardId)), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{award_id}")
    public ResponseEntity<?> updateAward(@PathVariable("award_id") Long awardId, @RequestBody AwardDto awardRequest) {
        awardService.editAward(awardId, awardRequest);
        return new ResponseEntity<>("Award update.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{award_id}")
    public ResponseEntity<?> deleteAward(@PathVariable("award_id") Long awardId) {
        awardService.deleteAward(awardId);
        return new ResponseEntity<>("Award deleted.", HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> findAllAwards() {
        return new ResponseEntity<>(awardMapper.toDtoList((List<Award>) awardService.findAllAwards()), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{award_id}/add-winner/{movie_id}")
    public ResponseEntity<?> addWinner(@PathVariable("award_id") Long awardId, @PathVariable("movie_id") Long movieId) {
        awardService.assignAward(awardId, movieId);
        return new ResponseEntity<>("Award assigned.", HttpStatus.ACCEPTED);
    }

    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<?> getMovieAwards(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(awardMapper.toDtoList((List<Award>) awardService.awardsFromMovie(movieId)), HttpStatus.ACCEPTED);
    }

    //awards from a movie

}
