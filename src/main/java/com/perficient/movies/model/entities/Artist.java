package com.perficient.movies.model.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Column;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Artist extends Person implements Serializable {

    @NotNull
    @NotEmpty
    private String bio;

    @Column(name = "is_active")
    @NotNull
    private boolean isActive;

    public Artist(String name, String lastName, String bio, boolean isActive) {
        super(name, lastName);
        this.bio = bio;
        this.isActive = isActive;
    }
}
