package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.ActividadMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.ParticipanteMapper;
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
import jakarta.transaction.Transactional;
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
    private ActividadMapper actividadMapper;

    @Autowired
    private IParticipanteService participanteService;

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

    @Transactional
    @Override
    public ActividadResponseDTO crearActividad(ActividadRequestDTO dto) {

        validarActividadRequestDTO(dto);

        //Buscar plan
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("El plan no existe"));

        TipoPlan tipoPlan = plan.getTipoPlan();

        //Obtener usuario
        UsuarioResponseDTO usuarioEncargado = obtenerUsuarioPorCedula(dto.getCedulaEncargado());

        //Obtener orientador(en caso de que sea actividad de bienestar o capacitacion)
        Long idUsuarioOrientador = null;

        if (tipoPlan == TipoPlan.BIENESTAR || tipoPlan == TipoPlan.CAPACITACION) {
            if (dto.getCedulaOrientador() != null) {
                idUsuarioOrientador = obtenerUsuarioPorCedula(dto.getCedulaOrientador()).getId();
            }
        }

        //Crear Actividad
        Actividad actividad = new Actividad();
        actividad.setNombre(dto.getNombre());
        actividad.setPlan(plan);
        /*
        actividad.setIdEncargado(usuarioEncargado.getId());
        actividad.setIdOrientador(idOrientador);

         */

        // Guardar actividad
        actividad = actividadRepository.save(actividad);

        //Crear Participante para ENCARGADO
        Participante participanteEncargado = new Participante();
        participanteEncargado.setIdUsuario(usuarioEncargado.getId());
        participanteEncargado.setActividad(actividad);
        participanteEncargado = participanteRepository.save(participanteEncargado);

        // 6) Crear participante orientador si aplica
        Participante participanteOrientador = null;
        if (idUsuarioOrientador != null) {
            participanteOrientador = new Participante();
            participanteOrientador.setIdUsuario(idUsuarioOrientador);
            participanteOrientador.setActividad(actividad);
            participanteOrientador = participanteRepository.save(participanteOrientador);
        }

        actividad.setIdEncargado(participanteEncargado.getId());
        actividad.setIdOrientador(participanteOrientador != null ? participanteOrientador.getId() : null);

        //Armar respuesta
        ParticipanteResponseDTO encargadoResponse = ParticipanteResponseDTO.builder()
                .idParticipante(participanteEncargado.getId())
                .usuario(usuarioEncargado)
                .build();

        return actividadMapper.toResponse(actividad, encargadoResponse,planMapper.toResponse(plan),new ArrayList<>());
    }


    @Transactional
    @Override
    public ActividadResponseDTO actualizarActividad(Long id, ActividadRequestDTO dto) {

        validarActividadRequestDTO(dto);

        // 1) Buscar actividad
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La actividad a actualizar no existe"));

        // 2) Buscar plan
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("El plan no existe"));

        TipoPlan tipoPlan = plan.getTipoPlan();

        // 3) Obtener usuario encargado
        UsuarioResponseDTO usuarioEncargado = obtenerUsuarioPorCedula(dto.getCedulaEncargado());

        // 4) Obtener usuario orientador si corresponde
        Long idUsuarioOrientador = null;
        if (tipoPlan == TipoPlan.BIENESTAR || tipoPlan == TipoPlan.CAPACITACION) {
            if (dto.getCedulaOrientador() != null) {
                idUsuarioOrientador = obtenerUsuarioPorCedula(dto.getCedulaOrientador()).getId();
            }
        }

        // 5) Actualizar datos principales de Actividad
        actividad.setNombre(dto.getNombre());
        actividad.setPlan(plan);
        actividad.setIdEncargado(null);   // se asigna después
        actividad.setIdOrientador(null);  // se asigna después

        actividad = actividadRepository.save(actividad); // guardar primero

        // 6) Eliminar participantes anteriores
        actividad.getParticipantes().clear();  // orphanRemoval lo elimina de la DB

        // 7) Crear participante ENCARGADO
        Participante participanteEncargado = new Participante();
        participanteEncargado.setIdUsuario(usuarioEncargado.getId());
        participanteEncargado.setActividad(actividad);
        participanteEncargado = participanteRepository.save(participanteEncargado);

        // 8) Crear participante ORIENTADOR si aplica
        Participante participanteOrientador = null;
        if (idUsuarioOrientador != null) {
            participanteOrientador = new Participante();
            participanteOrientador.setIdUsuario(idUsuarioOrientador);
            participanteOrientador.setActividad(actividad);
            participanteOrientador = participanteRepository.save(participanteOrientador);
        }

        // 9) Asociar IDs de PARTICIPANTE a Actividad (no idUsuario)
        actividad.setIdEncargado(participanteEncargado.getId());
        actividad.setIdOrientador(
                participanteOrientador != null ? participanteOrientador.getId() : null
        );

        actividad = actividadRepository.save(actividad);

        // 10) Construir respuesta
        ParticipanteResponseDTO encargadoResponse = ParticipanteResponseDTO.builder()
                .idParticipante(participanteEncargado.getId())
                .usuario(usuarioEncargado)
                .build();

        return actividadMapper.toResponse(
                actividad,
                encargadoResponse,
                planMapper.toResponse(plan),
                new ArrayList<>()
        );
    }



    @Override
    public Boolean eliminarActividad(Long id) {

        Actividad actividad = actividadRepository.findById(id).orElseThrow(() -> new RuntimeException("La actividad a eliminar no existe"));
        actividadRepository.delete(actividad);

        return true;
    }

    @Override
    public ActividadResponseDTO getActividad(Long id) {
        Actividad actividad = actividadRepository.findById(id).orElseThrow(() -> new RuntimeException("La actividad a buscar no existe"));
        System.out.println("Buscando participante con id"+actividad.getIdEncargado());
        ParticipanteResponseDTO encargado = participanteService.encontrarPorId(actividad.getIdEncargado());
        return actividadMapper.toResponse(actividad, encargado, planMapper.toResponse(actividad.getPlan()), null);
    }

    @Override
    public List<ActividadResponseDTO> getActividades() {
        return List.of();
    }



}
