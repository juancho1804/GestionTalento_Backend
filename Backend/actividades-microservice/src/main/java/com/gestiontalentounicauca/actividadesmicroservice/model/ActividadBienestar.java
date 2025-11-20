package com.gestiontalentounicauca.actividadesmicroservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class ActividadBienestar extends Actividad {
    private String campoAdicionalBienestar;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MotivoPago> motivosPago = new ArrayList<>();
}
