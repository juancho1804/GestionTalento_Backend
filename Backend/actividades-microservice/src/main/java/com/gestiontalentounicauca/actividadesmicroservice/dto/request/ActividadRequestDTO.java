package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class ActividadRequestDTO {

    private Long planId;
    private String nombre;
    private String cedulaEncargado;
    private String cedulaOrientador;

}
