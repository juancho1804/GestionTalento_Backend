package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanRequestDTO {
    private String nombre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    private Float presupuestoAsignado;

}
