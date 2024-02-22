package com.perficient.movies.controllers;

import com.perficient.movies.model.dtos.AwardDto;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.mappers.AwardMapper;
import com.perficient.movies.services.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    @Autowired
    private AwardService<Award> awardService;

    @Autowired
    private AwardMapper awardMapper;

    @PostMapping
    public ResponseEntity<String> createAward(@RequestBody AwardDto awardRequest) {

        awardService.createAward(awardMapper.toEntity(awardRequest));

        return new ResponseEntity<>("Award created.", HttpStatus.CREATED);
    }

    @GetMapping("/{award_id}")
    public ResponseEntity<AwardDto> findAward(@PathVariable("award_id") Long awardId) {
        return new ResponseEntity<>(awardMapper.toDto(awardService.findAward(awardId)), HttpStatus.OK);
    }

    @PutMapping("/{award_id}")
    public ResponseEntity<String> updateAward(@PathVariable("award_id") Long awardId, @RequestBody AwardDto awardRequest) {
        awardService.editAward(awardId, awardRequest);
        return new ResponseEntity<>("Award update.", HttpStatus.OK);
    }

    @DeleteMapping("/{award_id}")
    public ResponseEntity<String> deleteAward(@PathVariable("award_id") Long awardId) {
        awardService.deleteAward(awardId);
        return new ResponseEntity<>("Award deleted.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AwardDto>> findAllAwards() {
        return new ResponseEntity<>(awardMapper.toDtoList((List<Award>) awardService.findAllAwards()), HttpStatus.OK);
    }

    @PatchMapping("/{award_id}/winner/{movie_id}")
    public ResponseEntity<String> addWinner(@PathVariable("award_id") Long awardId, @PathVariable("movie_id") Long movieId) {
        awardService.assignAward(awardId, movieId);
        return new ResponseEntity<>("Award assigned.", HttpStatus.OK);
    }

    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<List<AwardDto>> getMovieAwards(@PathVariable("movie_id") Long movieId) {
        return new ResponseEntity<>(awardMapper.toDtoList((List<Award>) awardService.awardsFromMovie(movieId)), HttpStatus.OK);
    }

}
