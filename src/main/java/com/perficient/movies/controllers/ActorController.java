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

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorMapper actorMapper;

    @PostMapping
    public ResponseEntity<?> createActor(@RequestBody ActorDto actorRequest) {

        actorService.createActor(actorRequest.getName(), actorRequest.getLastName(),
                actorRequest.getBio(), actorRequest.isActive(), actorRequest.getBiggestRole(),
                actorRequest.getStartYear());

        return new ResponseEntity<>("Actor created.", HttpStatus.CREATED);
    }

    @GetMapping("/{actor_id}")
    public ResponseEntity<?> findActor(@PathVariable("actor_id") Long actorId) {
        return new ResponseEntity<>(actorMapper.toDto((Actor) actorService.findActor(actorId)), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{actor_id}")
    public ResponseEntity<?> updateActor(@PathVariable("actor_id") Long actorId, @RequestBody ActorDto actorRequest) {
        actorService.editActor(actorId, actorMapper.toEntity(actorRequest));
        return new ResponseEntity<>("Actor update.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{actor_id}")
    public ResponseEntity<?> deleteActor(@PathVariable("actor_id") Long actorId) {
        actorService.deleteActor(actorId);
        return new ResponseEntity<>("Actor deleted.", HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> findAllActors() {
        return new ResponseEntity<>(actorMapper.toDtoList((List<Actor>) actorService.findAllActors()), HttpStatus.ACCEPTED);
    }

}
