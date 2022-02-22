package com.perficient.movies.controllers;

import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.model.mappers.ActorMapper;
import com.perficient.movies.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorMapper actorMapper;

    @PostMapping
    public ResponseEntity<?> createActor(@RequestBody Actor actorRequest) {

        actorService.createActor(actorRequest.getName(), actorRequest.getLastName(),
                actorRequest.getBio(), actorRequest.getIsActive(), actorRequest.getBiggestRole(),
                actorRequest.getStartYear());

        return new ResponseEntity<>("Actor created.", HttpStatus.CREATED);
    }

    @GetMapping("/{actor_id}")
    public ResponseEntity<?> findActor(@PathVariable("actor_id") Long actorId) {
        return new ResponseEntity<>(actorMapper.toDTO((Actor) actorService.findActor(actorId)), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{actor_id}")
    public ResponseEntity<?> updateActor(@PathVariable("actor_id") Long actorId, @RequestBody Actor actorRequest) {
        actorService.editActor(actorId, actorRequest);
        return new ResponseEntity<>("Actor update.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{actor_id}")
    public ResponseEntity<?> deleteActor(@PathVariable("actor_id") Long actorId) {
        actorService.deleteActor(actorId);
        return new ResponseEntity<>("Actor deleted.", HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> findAllActors() {
        return new ResponseEntity<>(actorMapper.toDTOList((List<Actor>) actorService.findAllActors()), HttpStatus.ACCEPTED);
    }

}
