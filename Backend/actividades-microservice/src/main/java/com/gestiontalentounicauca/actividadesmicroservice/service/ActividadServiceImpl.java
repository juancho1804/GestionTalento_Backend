package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.ActividadFactory;
import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.PlanMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.*;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ActividadRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ParticipanteRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.PlanRepository;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuariosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActividadServiceImpl implements IActividadService {

    PlanMapper planMapper = new PlanMapper();
    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private UsuariosClient usuariosClient;
    @Autowired
    private ActividadFactory actividadFactory;


    //Metodos auxiliares
    private void validarActividadRequestDTO(ActividadRequestDTO actividadRequestDTO) {
        //Validar que la actividad este asociada a un plan
        if(actividadRequestDTO.getPlanId() == null){
            throw new RuntimeException("El plan es nulo");
        }

        //Validar que la cedula no este vacía
        if(actividadRequestDTO.getCedulaEncargado() == null){
            throw new RuntimeException("La cedula no puede estar vacia");
        }
    }

    private UsuarioResponseDTO obtenerUsuarioPorCedula(String cedula) {
        //Buscar el usuario asociado a la cedula del encargado de la actividad
        ResponseEntity<UsuarioResponseDTO> usuario = usuariosClient.getUsuarioByCedula(cedula);


        //Validar que el usuario existe
        if(usuario.getBody()==null){
            throw new RuntimeException("El usuario no existe");
        }
        return usuario.getBody();
    }

    @Override
    public ActividadResponseDTO crearActividad(ActividadRequestDTO actividadRequestDTO) {

        validarActividadRequestDTO(actividadRequestDTO);

        // Buscar el plan asociado a la actividad
        Plan plan = planRepository.findById(actividadRequestDTO.getPlanId()).orElseThrow(() -> new RuntimeException("El plan no existe"));

        //Buscar el usuario que se encargará de la actividad
        UsuarioResponseDTO usuario = obtenerUsuarioPorCedula(actividadRequestDTO.getCedulaEncargado());

        // Crear objeto encargado
        Participante encargado = new Participante();

        //Pasar id del usuario al encargado
        encargado.setIdUsuario(usuario.getId());
        //Guardar encargado
        encargado = participanteRepository.save(encargado);

        //Crear actividad
        Actividad actividad = actividadFactory.crearActividad(actividadRequestDTO, plan, encargado);

        actividad.setNombre(actividadRequestDTO.getNombre());
        actividad.setEncargado(encargado);
        actividad.setPlan(plan);

        // Crear objeto encargadoResponse
        ParticipanteResponseDTO encargadoResponse = ParticipanteResponseDTO.builder()
                .idParticipante(encargado.getId())
                .usuario(usuario)
                .build();

        //Guardar actividad
        actividad = actividadRepository.save(actividad);

        //Asignar actividad a encargado
        encargado.setActividad(actividad);
        //"Actualizar" encargado
        participanteRepository.save(encargado);



        //Crear objeto actividad response
        ActividadResponseDTO actividadResponseDTO = ActividadResponseDTO.builder()
                .nombre(actividad.getNombre())
                .encargado(encargadoResponse)
                .plan(planMapper.toResponse(plan))
                .participantes(new ArrayList<>())
                .build();


        return actividadResponseDTO;
    }

    @Override
    public ActividadResponseDTO actualizarActividad(Long id, ActividadRequestDTO actividadRequestDTO) {
        return null;
    }

    @Override
    public Boolean eliminarActividad(Long id) {
        return null;
    }

    @Override
    public ActividadResponseDTO getActividad(Long id) {
        return null;
    }

    @Override
    public List<ActividadResponseDTO> getActividades() {
        return List.of();
    }



}
