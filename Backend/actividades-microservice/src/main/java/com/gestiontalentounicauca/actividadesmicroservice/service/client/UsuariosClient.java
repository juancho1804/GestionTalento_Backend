package com.gestiontalentounicauca.actividadesmicroservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuarios-service",url = "http://localhost:8001/api/usuario")
public interface UsuariosClient {
    @GetMapping("/cedula/{cedula}")
    ResponseEntity<UsuarioResponseDTO> getUsuarioByCedula(@PathVariable("cedula") String cedula);

    @GetMapping("/id/{id}")
    ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable("id") Long id);

    @PostMapping
    ResponseEntity<UsuarioResponseDTO> createUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO);
}
