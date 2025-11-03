package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ActividadRequestDTO {

    private Long planId;
    private String nombre;
    private String cedulaEncargado;

    private String campoAdicionalBienestar; //Adicional
    private String campoAdicionalCapacitacion; //Adicional

}
