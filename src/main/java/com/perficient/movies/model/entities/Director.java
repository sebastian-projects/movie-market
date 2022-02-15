package com.perficient.movies.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "directors")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Director extends Artist implements Serializable {

    @Column(name = "main_gender")
    private String mainGender;

    @Column(name = "has_directed_series")
    private boolean hasDirectedSeries;

}
