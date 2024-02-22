package com.perficient.movies.model.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "actors")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Actor extends Artist implements Serializable {

    @Column(name = "start_year")
    @NotNull
    private int startYear;

    @Column(name = "biggest_role")
    @NotNull
    @NotEmpty
    private String biggestRole;

    public Actor(String name, String lastName, String bio, Boolean isActive, int startYear, String biggestRole) {
        super(name, lastName, bio, isActive);
        this.startYear = startYear;
        this.biggestRole = biggestRole;
    }
}
