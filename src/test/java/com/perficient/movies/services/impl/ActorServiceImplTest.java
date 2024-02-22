package com.perficient.movies.services.impl;

import com.perficient.movies.exceptions.BusinessException;
import com.perficient.movies.exceptions.ErrorCodesEnum;
import com.perficient.movies.model.entities.Actor;
import com.perficient.movies.repositories.ActorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@Transactional
@SpringBootTest
class ActorServiceImplTest {

    @Autowired
    private ActorServiceImpl actorService;

    @MockBean
    private ActorRepository actorRepository;

    @Test
    void createActorTest() {

        String bio = "Adam Douglas Driver (born November 19, 1983) is an American actor.";

        Actor actor = new Actor("Adam", "Driver", bio, true, 2010, "Kylo Ren");

        when(actorRepository.save(actor)).thenReturn(actor);

        actorService.createActor(actor);

        verify(actorRepository, times(1)).save(actor);

    }

    @Test
    void findActorTest() {
        long actorId = 1L;

        when(actorRepository.findById(1l)).thenReturn(Optional.of(new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren")));

        actorService.findActor(actorId);

        verify(actorRepository, times(1)).findById(1L);
    }

    @Test
    void actorNotFoundTest() {
        long actorId = 1L;

        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> actorService.findActor(actorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND, exception.getCode());

        verify(actorRepository, times(1)).findById(actorId);
    }

    @Test
    void findAllActorsTest() {
        Actor actor1 = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        List<Actor> actors = List.of(actor1, actor1, actor1);

        when(actorRepository.findAll()).thenReturn(actors);

        List<Actor> actorsService = (List<Actor>) actorService.findAllActors();

        verify(actorRepository, times(1)).findAll();

        Assertions.assertEquals(3, actorsService.size());
    }

    @Test
    void editActorTest() {
        long actorId = 1L;

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        when(actorRepository.save(actor)).thenReturn(actor);

        actorService.editActor(actorId, actor);

        verify(actorRepository, times(1)).save(actor);

        verify(actorRepository, times(1)).findById(1L);

    }

    @Test
    void editActorNotFound() {
        long actorId = 1L;

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> actorService.editActor(actorId, actor));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND, exception.getCode());

        verify(actorRepository, times(0)).save(actor);

        verify(actorRepository, times(1)).findById(1L);
    }

    @Test
    void deleteActorTest() {
        long actorId = 1L;

        Actor actor = new Actor("Adam", "Driver", "bio", true, 2010, "Kylo Ren");

        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        doNothing().when(actorRepository).delete(actor);

        actorService.deleteActor(actorId);

        verify(actorRepository, times(1)).delete(actor);

        verify(actorRepository, times(1)).findById(actorId);
    }

    @Test
    void deleteActorNotFound() {
        long actorId = 1L;

        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> actorService.deleteActor(actorId));

        Assertions.assertEquals("Error", exception.getMessage());

        Assertions.assertEquals(ErrorCodesEnum.ACTOR_NOT_FOUND, exception.getCode());

        verify(actorRepository, times(0)).delete(new Actor());

        verify(actorRepository, times(1)).findById(1L);
    }
}