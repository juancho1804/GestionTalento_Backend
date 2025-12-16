package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "MotivoPagoRequest",
        description = "Datos necesarios para registrar un motivo de pago asociado a una actividad"
)
public class MotivoPagoRequestDTO {

    @Schema(
            description = "Identificador del tipo de motivo de pago",
            example = "3"
    )
    private Long idTipoMotivoPago;

    @Schema(
            description = "Nombre del tipo de motivo de pago",
            example = "Transporte"
    )
    private String nombreTipoMotivoPago;

    @Schema(
            description = "Monto asignado al motivo de pago",
            example = "150000"
    )
    private Double monto;

    @Schema(
            description = "ID de la actividad a la que se asocia el motivo de pago",
            example = "12"
    )
    private Long idActividad;
}
