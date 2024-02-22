package com.perficient.movies.model.mappers;

import com.perficient.movies.model.dtos.AwardDto;
import com.perficient.movies.model.dtos.DirectorDto;
import com.perficient.movies.model.entities.Award;
import com.perficient.movies.model.entities.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AwardMapper {
    AwardMapper INSTANCE = Mappers.getMapper(AwardMapper.class);

    Award toEntity(AwardDto awardDto);

    @Mapping(source = "movie.name", target = "movieName")
    AwardDto toDto(Award award);

    default List<AwardDto> toDtoList(List<Award> awardList) {
        if (awardList == null) {
            return new ArrayList<>();
        }
        return awardList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
