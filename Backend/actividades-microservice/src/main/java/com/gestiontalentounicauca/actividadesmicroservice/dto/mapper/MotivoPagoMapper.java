package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.MotivoPago;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoMotivoPago;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MotivoPagoMapper {

    public MotivoPago ToEntity(MotivoPagoRequestDTO motivoPagoRequestDTO, TipoMotivoPago tipoMotivoPago, Actividad actividad) {
        MotivoPago motivoPago = MotivoPago.builder().dominio(tipoMotivoPago).montoAsignado(motivoPagoRequestDTO.getMonto())
                .actividad(actividad).build();
        return motivoPago;
    }

    public MotivoPagoResponseDTO toResponseDTO(MotivoPago motivoPago) {
        MotivoPagoResponseDTO motivoPagoResponseDTO = MotivoPagoResponseDTO.builder()
                .id(motivoPago.getId()).idTipoMotivoPago(motivoPago.getDominio().getId())
                .montoAsignado(motivoPago.getMontoAsignado()).idActividad(motivoPago.getActividad().getId()).build();
        return motivoPagoResponseDTO;
    }
}
