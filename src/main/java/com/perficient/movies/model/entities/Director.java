package com.perficient.movies.model.entities;

import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "directors")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Director extends Artist implements Serializable {

    @Column(name = "main_gender")
    @NotNull
    private String mainGender;

    @Column(name = "has_directed_series")
    @NotNull
    private boolean hasDirectedSeries;

    public Director(String name, String lastName, String bio, boolean isActive, String mainGender, boolean hasDirectedSeries) {
        super(name, lastName, bio, isActive);
        this.mainGender = mainGender;
        this.hasDirectedSeries = hasDirectedSeries;
    }
}
