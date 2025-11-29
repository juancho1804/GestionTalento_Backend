package com.gestiontalentounicauca.actividadesmicroservice.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService {
    @Autowired
    private UsuariosClient usuariosClient;

    private UsuarioResponseDTO obtenerUsuarioPorCedula(String cedula) {
        //Buscar el usuario asociado a la cedula del encargado de la actividad
        ResponseEntity<UsuarioResponseDTO> usuario = usuariosClient.getUsuarioByCedula(cedula);


        //Validar que el usuario existe
        if(usuario.getBody()==null){
            throw new RuntimeException("El usuario no existe");
        }
        return usuario.getBody();
    }

    private UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        //Buscar el usuario asociado a la cedula del encargado de la actividad
        ResponseEntity<UsuarioResponseDTO> usuario = usuariosClient.getUsuarioById(id);


        //Validar que el usuario existe
        if(usuario.getBody()==null){
            throw new RuntimeException("El usuario no existe");
        }
        return usuario.getBody();
    }



}
