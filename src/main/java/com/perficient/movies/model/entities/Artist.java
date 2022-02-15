package com.perficient.movies.model.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Artist extends Person implements Serializable {

    private String bio;

    @Column(name = "is_active")
    private Boolean isActive;

}
