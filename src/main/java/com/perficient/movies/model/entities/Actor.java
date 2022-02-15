package com.perficient.movies.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "actors")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Actor extends Artist implements Serializable {

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "biggest_role")
    private String biggestRole;

}
