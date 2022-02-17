package com.perficient.movies.controllers;

import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/create")
    public void createActor(@RequestBody Actor actorRequest) {
        actorService.createActor(actorRequest.getName(), actorRequest.getLastName(),
                actorRequest.getBio(), actorRequest.getIsActive(), actorRequest.getBiggestRole(),
                actorRequest.getStartYear());
    }

    @GetMapping("/{actor_id}")
    public Actor findActor(@PathVariable("actor_id") Long actorId) {
        return actorService.findActor(actorId);
    }

    @PutMapping("/{actor_id}/update")
    public void updateActor(@PathVariable("actor_id") Long actorId, @RequestBody Actor actorRequest) {
        actorService.editActor(actorId, actorRequest);
    }

    @DeleteMapping("/{actor_id}")
    public void deleteActor(@PathVariable("actor_id") Long actorId) {
        actorService.deleteActor(actorId);
    }

    @GetMapping
    public List<Actor> findAllActor() {
        return actorService.findAllActors();
    }

}
