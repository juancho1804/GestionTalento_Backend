package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.PlanMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
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

    @Override
    public ActividadResponseDTO crearActividad(ActividadRequestDTO actividadRequestDTO) {

        //Validar que la actividad este asociada a un plan
        if(actividadRequestDTO.getPlanId() == null){
            throw new RuntimeException("El plan es nulo");
        }
        // Buscar el plan asociado a la actividad
        Plan plan = planRepository.findById(actividadRequestDTO.getPlanId()).orElse(null);
        if(plan == null){
            throw new RuntimeException("El plan no existe");
        }

        //Buscar el usuario asociado a la cedula del encargado de la actividad
        ResponseEntity<UsuarioResponseDTO> usuario = usuariosClient.getUsuarioByCedula(actividadRequestDTO.getCedulaEncargado());


        //Validar que el usuario existe
        if(usuario.getBody()==null){
            throw new RuntimeException("El usuario no existe");
        }

        // Crear objeto encargado
        Participante encargado = new Participante();

        //Pasar id del usuario al encargado
        encargado.setIdUsuario(usuario.getBody().getId());
        //Guardar encargado
        encargado = participanteRepository.save(encargado);

        // Crear objeto actividad ENTIDAD
        Actividad actividad = Actividad.builder()
                .nombre(actividadRequestDTO.getNombre())
                .encargado(encargado)
                .plan(plan).build();

        // Crear objeto encargadoResponse
        ParticipanteResponseDTO encargadoResponse = ParticipanteResponseDTO.builder()
                .idParticipante(encargado.getId())
                .actividad(ActividadResponseDTO.builder().build())
                .usuario(usuario.getBody())
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

        encargadoResponse.setActividad(actividadResponseDTO);
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
