package com.gestiontalentounicauca.actividadesmicroservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "plan")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaInicio;
    @Column(nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades;


    @Column
    private Float presupuestoAsignado;

    @Column
    private Float presupuestoEjecutado;
}
