package com.gestiontalentounicauca.actividadesmicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class ActividadCapacitacion extends Actividad{
    private String campoAdicionalCapacitacion;
}
