package com.perficient.movies.model.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Artist extends Person implements Serializable {

    private String bio;

    @Column(name = "is_active")
    private Boolean isActive;

    public Artist(String name, String lastName, String bio, Boolean isActive) {
        super(name, lastName);
        this.bio = bio;
        this.isActive = isActive;
    }
}
