package com.gestiontalentounicauca.actividadesmicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class ActividadBienestar extends Actividad {
    private String campoAdicionalBienestar;
}
