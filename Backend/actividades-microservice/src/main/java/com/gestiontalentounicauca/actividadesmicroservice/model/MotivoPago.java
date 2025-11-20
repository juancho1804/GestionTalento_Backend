package com.gestiontalentounicauca.actividadesmicroservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MotivoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dominio", nullable = false)
    private TipoMotivoPago dominio;

    @Column(nullable = false)
    private Double montoAsignado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;


}
