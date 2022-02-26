package com.perficient.movies.model.mappers;

import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.entities.Director;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorMapper INSTANCE = Mappers.getMapper(DirectorMapper.class);

    Director toEntity(DirectorDto directorDto);

    DirectorDto toDto(Director director);

    default List<DirectorDto> toDtoList(List<Director> directorList) {
        if (directorList == null) {
            return new ArrayList<>();
        }
        return directorList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
