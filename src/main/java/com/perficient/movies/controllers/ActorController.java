package com.perficient.movies.controllers;

import com.perficient.movies.model.dtos.ActorDto;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.mappers.ActorMapper;
import com.perficient.movies.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService<Actor> actorService;

    @Autowired
    private ActorMapper actorMapper;

    @PostMapping
    public ResponseEntity<String> createActor(@RequestBody Actor actorRequest) {

        actorService.createActor(actorRequest);

        return new ResponseEntity<>("Actor created.", HttpStatus.CREATED);
    }

    @GetMapping("/{actor_id}")
    public ResponseEntity<ActorDto> findActor(@PathVariable("actor_id") Long actorId) {
        return new ResponseEntity<>(actorMapper.toDto(actorService.findActor(actorId)), HttpStatus.OK);
    }

    @PutMapping("/{actor_id}")
    public ResponseEntity<String> updateActor(@PathVariable("actor_id") Long actorId, @RequestBody ActorDto actorRequest) {
        actorService.editActor(actorId, actorMapper.toEntity(actorRequest));
        return new ResponseEntity<>("Actor update.", HttpStatus.OK);
    }

    @DeleteMapping("/{actor_id}")
    public ResponseEntity<String> deleteActor(@PathVariable("actor_id") Long actorId) {
        actorService.deleteActor(actorId);
        return new ResponseEntity<>("Actor deleted.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActorDto>> findAllActors() {
        return new ResponseEntity<>(actorMapper.toDtoList((List<Actor>) actorService.findAllActors()), HttpStatus.OK);
    }

}
