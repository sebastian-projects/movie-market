package com.perficient.movies.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DirectorDto {

    private String name;

    private String lastName;

    private String bio;

    private boolean isActive;

    private String mainGender;

    private boolean hasDirectedSeries;
}
