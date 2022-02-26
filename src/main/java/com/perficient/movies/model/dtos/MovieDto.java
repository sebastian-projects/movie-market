package com.perficient.movies.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class MovieDto {
    private String name;

    private int duration;

    private String plot;

    private Date releaseDate;
}
