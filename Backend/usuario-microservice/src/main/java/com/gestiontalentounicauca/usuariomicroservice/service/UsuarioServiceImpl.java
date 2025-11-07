package com.gestiontalentounicauca.usuariomicroservice.service;

import com.gestiontalentounicauca.usuariomicroservice.dto.mapper.UsuarioMapper;
import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import com.gestiontalentounicauca.usuariomicroservice.model.RolModel;
import com.gestiontalentounicauca.usuariomicroservice.model.UsuarioModel;
import com.gestiontalentounicauca.usuariomicroservice.repository.RolRepository;
import com.gestiontalentounicauca.usuariomicroservice.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private IGoogleSheetsService googleSheetsService;



    private Set<RolModel>buscarRolesDeUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        //Extraer roles del dto
        Set<EnumRol> nombresRoles = usuarioRequestDTO.getRoles()
                .stream()
                .map(RolRequestDTO::getRol)
                .collect(Collectors.toSet());
        //Buscar roles en la bd
        Set<RolModel> rolesEncontrados = rolRepository.findAllByRolIn(nombresRoles);

        // Validar que todos existan
        if (rolesEncontrados.size() != nombresRoles.size()) {
            Set<EnumRol> faltantes = new HashSet<>(nombresRoles);
            faltantes.removeAll(
                    rolesEncontrados.stream()
                            .map(RolModel::getRol)
                            .collect(Collectors.toSet())
            );
            throw new EntityNotFoundException("Los siguientes roles no existen: " + faltantes);
        }
        return rolesEncontrados;
    }
    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO){
        UsuarioModel usuarioModel = usuarioMapper.toEntity(usuarioRequestDTO, new UsuarioModel());
        if(!usuarioRequestDTO.getRoles().isEmpty()){
            usuarioModel.setRoles(buscarRolesDeUsuario(usuarioRequestDTO));
        }else{
            Set<RolModel>rol = new HashSet<>();
            rol.add(rolRepository.getRolModelByRol(EnumRol.PARTICIPANTE));
            usuarioModel.setRoles(rol);
        }
        return usuarioMapper.toResponse(usuarioRepository.save(usuarioModel));
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        //Buscar si el usuario esta en la BD
        UsuarioModel usuarioModel = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuarioMapper.toEntity(usuarioRequestDTO, usuarioModel);
        usuarioModel.setRoles(buscarRolesDeUsuario(usuarioRequestDTO));

        return usuarioMapper.toResponse(usuarioRepository.save(usuarioModel));
    }

    @Override
    public boolean eliminarUsuario(Long id) {
        boolean respuesta = usuarioRepository.existsById(id);
        if (!respuesta) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
        return respuesta;
    }

    @Override
    public UsuarioResponseDTO obtenerUsuario(Long id) {
        UsuarioModel usuarioModel = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return usuarioMapper.toResponse(usuarioModel);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<UsuarioModel> usuarioModels = usuarioRepository.findAll();

        return usuarioModels.stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> agregarUsuarios(String spreadsheetId) throws IOException {

        List<List<Object>> response = googleSheetsService.getResponses(spreadsheetId);
        List<UsuarioResponseDTO> respuesta = new ArrayList<>();

        for(int i = 0; i < response.size(); i++){
            List<Object> row = response.get(i);
            UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
            usuarioRequestDTO.setNombre((String) row.get(0));
            usuarioRequestDTO.setApellido((String) row.get(1));
            usuarioRequestDTO.setCorreo((String) row.get(2));
            usuarioRequestDTO.setCedula((String) row.get(3));
            respuesta.add(this.crearUsuario(usuarioRequestDTO));
        }

        System.out.println(response.size());

        return respuesta;
    }

    @Override
    public UsuarioResponseDTO buscarUsuarioPorCedula(String cedula) {
        UsuarioModel usuarioModel = usuarioRepository.findByCedula(cedula);
        if(usuarioModel==null){
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        return usuarioMapper.toResponse(usuarioModel);
    }

}
