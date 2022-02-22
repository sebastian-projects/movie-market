package com.perficient.movies.model.mappers;

import com.perficient.movies.model.dtos.ActorDTO;
import com.perficient.movies.model.entities.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    Actor toEntity(ActorDTO actorDTO);

    ActorDTO toDTO(Actor actor);

    default List<ActorDTO> toDTOList(List<Actor> actorList) {
        if (actorList == null) {
            return new ArrayList<>();
        }
        return actorList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
