package com.gestiontalentounicauca.usuariomicroservice.dto.mapper;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.model.RolModel;
import com.gestiontalentounicauca.usuariomicroservice.model.UsuarioModel;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UsuarioMapper {
    public UsuarioModel toEntity(UsuarioRequestDTO usuarioRequestDTO, UsuarioModel usuarioModel) {
        usuarioModel.setCorreo(usuarioRequestDTO.getCorreo());
        usuarioModel.setCedula(usuarioRequestDTO.getCedula());
        usuarioModel.setNombre(usuarioRequestDTO.getNombre());
        usuarioModel.setApellido(usuarioRequestDTO.getApellido());
        return usuarioModel;
    }

    public UsuarioResponseDTO toResponse(UsuarioModel usuarioModel){
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuarioModel.getId());
        usuarioResponseDTO.setNombre(usuarioModel.getNombre());
        usuarioResponseDTO.setCorreo(usuarioModel.getCorreo());
        usuarioResponseDTO.setCedula(usuarioModel.getCedula());
        usuarioResponseDTO.setApellido(usuarioModel.getApellido());

        Set<String> rolesString = new HashSet<>();
        for(RolModel rolModel : usuarioModel.getRoles()){
            rolesString.add(rolModel.getRol().name());
        }
        usuarioResponseDTO.setRoles(rolesString);

        return usuarioResponseDTO;
    }
}
