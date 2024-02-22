package com.perficient.movies.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwardDto {

    private String name;

    private String organization;

    private int year;

    private String movieName;

    public AwardDto(String name, String organization, int year) {
        this.name = name;
        this.organization = organization;
        this.year = year;
    }
}
