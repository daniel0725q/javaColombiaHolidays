package com.quinterodaniel.holidays.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "festivo")
@Getter
public class Holiday {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "dia")
    private Integer day;

    @Column(name = "mes")
    private Integer month;

    @Column(name = "diaspascua")
    private Integer easter;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idtipo")
    private Type type;
}
