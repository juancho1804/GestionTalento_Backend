package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.crearUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(usuarioResponseDTO,null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id,@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.actualizarUsuario(id, usuarioRequestDTO);
        return new ResponseEntity<>(usuarioResponseDTO,null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> eliminarUsuario(@RequestParam Long id){
        boolean respuesta = usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(respuesta,null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id){
        return new ResponseEntity<>(usuarioService.obtenerUsuario(id),null, HttpStatus.OK);
    }
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorCedula(@PathVariable String cedula){
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorCedula(cedula),null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>>obtenerUsuariosPorId(){
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios,null, HttpStatus.OK);
    }

    @PostMapping("/2")
    public List<UsuarioResponseDTO> agregarUsuariosDesdeSheets(@RequestParam String spreadsheetId) throws IOException {
        List<UsuarioResponseDTO> usuarios = usuarioService.agregarUsuarios(spreadsheetId);
        return usuarios;
    }

}
