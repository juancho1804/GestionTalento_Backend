package com.gestiontalentounicauca.actividadesmicroservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evidencia")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String rutaArchivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;
}
