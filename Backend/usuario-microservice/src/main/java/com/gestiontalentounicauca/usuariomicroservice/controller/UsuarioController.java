package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(
        name = "Usuarios",
        description = "Operaciones relacionadas con la gestión de usuarios del sistema"
)
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // ------------------ CREAR USUARIO ------------------
    @Operation(
            summary = "Crear un usuario",
            description = "Registra un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioResponseDTO =
                usuarioService.crearUsuario(usuarioRequestDTO);

        return new ResponseEntity<>(usuarioResponseDTO, null, HttpStatus.CREATED);
    }

    // ------------------ ACTUALIZAR USUARIO ------------------
    @Operation(
            summary = "Actualizar un usuario",
            description = "Actualiza la información de un usuario existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioResponseDTO =
                usuarioService.actualizarUsuario(id, usuarioRequestDTO);

        return new ResponseEntity<>(usuarioResponseDTO, null, HttpStatus.OK);
    }

    // ------------------ ELIMINAR USUARIO ------------------
    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina un usuario a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> eliminarUsuario(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @RequestParam Long id) {

        boolean respuesta = usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(respuesta, null, HttpStatus.NO_CONTENT);
    }

    // ------------------ OBTENER USUARIO POR ID ------------------
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Consulta la información de un usuario usando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable Long id) {

        return new ResponseEntity<>(
                usuarioService.obtenerUsuario(id),
                null,
                HttpStatus.OK
        );
    }

    // ------------------ OBTENER USUARIO POR CÉDULA ------------------
    @Operation(
            summary = "Obtener usuario por cédula",
            description = "Consulta un usuario a partir de su número de cédula"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorCedula(
            @Parameter(description = "Cédula del usuario", example = "1234567890", required = true)
            @PathVariable String cedula) {

        return new ResponseEntity<>(
                usuarioService.buscarUsuarioPorCedula(cedula),
                null,
                HttpStatus.OK
        );
    }

    // ------------------ LISTAR USUARIOS ------------------
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene el listado completo de usuarios registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {

        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, null, HttpStatus.OK);
    }

    // ------------------ CARGAR USUARIOS DESDE GOOGLE SHEETS ------------------
    @Operation(
            summary = "Importar usuarios desde Google Sheets",
            description = "Agrega usuarios al sistema usando un Spreadsheet ID de Google Sheets"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios importados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al procesar el archivo")
    })
    @PostMapping("/2")
    public List<UsuarioResponseDTO> agregarUsuariosDesdeSheets(
            @Parameter(
                    description = "ID del archivo de Google Sheets",
                    example = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms",
                    required = true
            )
            @RequestParam String spreadsheetId) throws IOException {

        return usuarioService.agregarUsuarios(spreadsheetId);
    }
}
