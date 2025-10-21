package com.gestiontalentounicauca.usuariomicroservice.service;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.RolResponseDTO;

public interface IRolService {
    RolResponseDTO crearRol(RolRequestDTO request);
}
