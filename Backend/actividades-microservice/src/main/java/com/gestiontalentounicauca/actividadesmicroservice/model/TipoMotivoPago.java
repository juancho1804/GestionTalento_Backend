package com.gestiontalentounicauca.actividadesmicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class TipoMotivoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
}
