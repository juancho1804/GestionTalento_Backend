package com.gestiontalentounicauca.usuariomicroservice.service;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.RolResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.model.RolModel;
import com.gestiontalentounicauca.usuariomicroservice.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private RolRepository rolRepository;


    @Override
    public RolResponseDTO crearRol(RolRequestDTO request) {

        RolModel rolModel = new RolModel();
        rolModel.setRol(request.getRol());

        rolModel = rolRepository.save(rolModel);
        RolResponseDTO rolResponseDTO = new RolResponseDTO();
        rolResponseDTO.setRol(rolModel.getRol());
        return rolResponseDTO;
    }
}
