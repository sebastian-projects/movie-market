package com.perficient.movies.model.mappers;

import com.perficient.movies.model.dtos.MovieDto;
import com.perficient.movies.model.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie toEntity(MovieDto movieDto);

    MovieDto toDto(Movie movie);

    default List<MovieDto> toDtoList(List<Movie> movieList) {
        if (movieList == null) {
            return new ArrayList<>();
        }
        return movieList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
