package com.gestiontalentounicauca.usuariomicroservice.repository;

import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel,Long> {
    UsuarioModel findByCedula(String cedula);
}
