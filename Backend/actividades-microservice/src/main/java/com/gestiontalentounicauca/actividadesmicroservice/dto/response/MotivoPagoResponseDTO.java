package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "MotivoPagoResponse",
        description = "Información del motivo de pago registrado"
)
public class MotivoPagoResponseDTO {

    @Schema(
            description = "Identificador único del motivo de pago",
            example = "5"
    )
    private Long id;

    @Schema(
            description = "Identificador del tipo de motivo de pago",
            example = "3"
    )
    private Long idTipoMotivoPago;

    @Schema(
            description = "Monto asignado al motivo de pago",
            example = "150000"
    )
    private Double montoAsignado;

    @Schema(
            description = "ID de la actividad asociada al motivo de pago",
            example = "12"
    )
    private Long idActividad;
}
