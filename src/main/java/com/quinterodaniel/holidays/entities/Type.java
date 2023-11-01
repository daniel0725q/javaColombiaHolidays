package com.quinterodaniel.holidays.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity(name = "tipo")
@Getter
public class Type {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo")
    private String type;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "type")
    List<Holiday> holidays;
}
