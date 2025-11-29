package com.gestiontalentounicauca.actividadesmicroservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "actividad")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private Long idEncargado;

    @Column
    private Long idOrientador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Participante> participantes = new ArrayList<>();

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MotivoPago> motivosPago = new ArrayList<>();



}
