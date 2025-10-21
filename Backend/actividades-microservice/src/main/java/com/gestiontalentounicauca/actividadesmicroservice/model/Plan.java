package com.gestiontalentounicauca.actividadesmicroservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "plan")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
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

    @Column
    private Float presupuestoAsignado;

    @Column
    private Float presupuestoPrevisto;

    @Column
    private Float presupuestoEjecutado;
}
