package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;

import java.util.List;

public interface IMotivoPagoService {
    MotivoPagoResponseDTO crearMotivoPago(MotivoPagoRequestDTO motivoPagoRequestDTO);
    MotivoPagoResponseDTO actualizarMotivoPago(Long idMotivoPago, MotivoPagoRequestDTO motivoPagoRequestDTO);
    Boolean eliminarMotivoPago(Long idMotivoPago);
    List<MotivoPagoResponseDTO> listarMotivoPagos();
    MotivoPagoResponseDTO encontrarPorId(Long idMotivoPago);

}
