package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MotivoPagoResponseDTO {
    private Long id;
    private Long idTipoMotivoPago;
    private Double montoAsignado;
    private Long idActividad;
}
