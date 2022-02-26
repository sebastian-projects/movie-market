package com.perficient.movies.model.mappers;

import com.perficient.movies.model.dtos.ActorDto;
import com.perficient.movies.model.entities.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    Actor toEntity(ActorDto actorDto);

    ActorDto toDto(Actor actor);

    default List<ActorDto> toDtoList(List<Actor> actorList) {
        if (actorList == null) {
            return new ArrayList<>();
        }
        return actorList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
