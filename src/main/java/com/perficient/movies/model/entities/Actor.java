package com.perficient.movies.model.entities;

import com.sun.istack.NotNull;
import lombok.*;
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
    private String biggestRole;

    public Actor(String name, String lastName, String bio, Boolean isActive, int startYear, String biggestRole) {
        super(name, lastName, bio, isActive);
        this.startYear = startYear;
        this.biggestRole = biggestRole;
    }
}
