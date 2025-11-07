package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.ActividadFactory;
import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.ParticipanteMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.Evidencia;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ActividadRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ParticipanteRepository;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuariosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipanteServiceImpl implements IParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private UsuariosClient usuariosClient;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ParticipanteMapper participanteMapper;


    public ParticipanteResponseDTO crearParticipante(ParticipanteRequestDTO participanteRequestDTO) {

        //Crear objeto participante
        Participante participante = new Participante();
        //Verificar si la cedula no esta vacia
        if(participanteRequestDTO.getCedula()==null) {
            throw new RuntimeException("La cedula no puede estar vacia");
        }

        //Verificar si la actividad no esta vacia
        if(participanteRequestDTO.getIdActividad()==null) {
            throw new RuntimeException("La actividad no puede estar vacia");
        }

        //Buscar usuario
        UsuarioResponseDTO usuarioResponseDTO = usuariosClient.
                getUsuarioByCedula(participanteRequestDTO.getCedula()).getBody();


        //Buscar la actividad
        Actividad actividad = actividadRepository.findById(participanteRequestDTO.getIdActividad()).get();


        //Verificar que el participante no este relacionado a la actividad
        for(Participante participante1: actividad.getParticipantes()){
            if(participante1.getIdUsuario().equals(usuarioResponseDTO.getId())){
                throw new RuntimeException("El usuario ya esta registrado en esta actividad");
            }
        }



        //Pasar la actividad a la entidad
        participante.setActividad(actividad);



        //Pasar id usuario al participante
        participante.setIdUsuario(usuarioResponseDTO.getId());

        //Guardar participante
        participante = participanteRepository.save(participante);

        ParticipanteResponseDTO participanteResponseDTO = ParticipanteResponseDTO.builder().idParticipante(participante.getId())
                .usuario(usuarioResponseDTO).idActividad(actividad.getId()).build();


        return participanteResponseDTO;

    }

    @Override
    public ParticipanteResponseDTO actualizarParticipante(Long idParticipante, ParticipanteRequestDTO participanteRequestDTO) {

        if(idParticipante==null) {
            throw new RuntimeException("La identificacion no puede estar vacia");
        }

        Participante participante = participanteRepository.findById(idParticipante).orElseThrow(()->new RuntimeException("No se encontró el participante"));

        //Verificar si la cedula no esta vacia
        if(participanteRequestDTO.getCedula()==null) {
            throw new RuntimeException("La cedula no puede estar vacia");
        }

        //Verificar si la actividad no esta vacia
        if(participanteRequestDTO.getIdActividad()==null) {
            throw new RuntimeException("La actividad no puede estar vacia");
        }

        if(participanteRequestDTO.getIdActividad().equals(participante.getActividad().getId())) {
            throw new RuntimeException("La actividad ya esta asignada al participante");
        }

        //Buscar usuario
        UsuarioResponseDTO usuarioResponseDTO = usuariosClient.
                getUsuarioByCedula(participanteRequestDTO.getCedula()).getBody();


        //Buscar la actividad
        Actividad actividad = actividadRepository.findById(participanteRequestDTO.getIdActividad()).orElseThrow(()->new RuntimeException("No se encontro actividad"));


        //Verificar que el participante no este relacionado a la actividad
        for(Participante participante1: actividad.getParticipantes()){
            if(participante1.getIdUsuario().equals(usuarioResponseDTO.getId())){
                throw new RuntimeException("El usuario ya esta registrado en esta actividad");
            }
        }



        //Pasar la actividad a la entidad
        participante.setActividad(actividad);


        //Guardar participante
        participante = participanteRepository.save(participante);

        ParticipanteResponseDTO participanteResponseDTO = ParticipanteResponseDTO.builder().idParticipante(participante.getId())
                .usuario(usuarioResponseDTO).idActividad(actividad.getId()).build();


        return participanteResponseDTO;
    }

    @Override
    public Boolean eliminarParticipante(Long idParticipante) {
        if(idParticipante==null) {
            throw new RuntimeException("La identificacion no puede estar vacia");
        }

        if(!participanteRepository.existsById(idParticipante)) {
            throw new RuntimeException("No se encontro al participante");
        }
        participanteRepository.deleteById(idParticipante);

        return true;
    }

    @Override
    public List<ParticipanteResponseDTO> listarParticipantes() {

        List<Participante> participantes= participanteRepository.findAll();

        return participantes.stream()
                .map(participante -> participanteMapper.toResponse(participante,usuariosClient.getUsuarioById(participante.getIdUsuario()).getBody()))
                .collect(Collectors.toList());

    }

    @Override
    public ParticipanteResponseDTO encontrarPorId(Long id) {

        if(id==null) {
            throw new RuntimeException("La identificacion no puede estar vacia");
        }

        Participante participanteModel = participanteRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro participante"));

        return participanteMapper.toResponse(participanteModel,usuariosClient.getUsuarioById(participanteModel.getIdUsuario()).getBody());
    }
}
