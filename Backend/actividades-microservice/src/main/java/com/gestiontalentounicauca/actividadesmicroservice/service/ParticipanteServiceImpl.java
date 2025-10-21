package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ActividadRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ParticipanteRepository;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuariosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipanteServiceImpl {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private UsuariosClient usuariosClient;

    @Autowired
    private ActividadRepository actividadRepository;


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



        //Buscar la actividad
        Actividad actividad = actividadRepository.findById(participanteRequestDTO.getIdActividad()).get();
        //Pasar la actividad a la entidad
        participante.setActividad(actividad);

        //Construir actividadResponse para pasarlo a el participante
        ActividadResponseDTO actividadResponseDTO =  ActividadResponseDTO.builder().
                nombre(actividad.getNombre()).build();

        //Buscar usuario
        UsuarioResponseDTO usuarioResponseDTO = usuariosClient.
                getUsuarioByCedula(participanteRequestDTO.getCedula()).getBody();

        //Pasar id usuario al participante
        participante.setIdUsuario(usuarioResponseDTO.getId());

        //Guardar participante
        participante = participanteRepository.save(participante);

        ParticipanteResponseDTO participanteResponseDTO = ParticipanteResponseDTO.builder().idParticipante(participante.getId())
                .usuario(usuarioResponseDTO).actividad(actividadResponseDTO).build();


        return participanteResponseDTO;

    }
}
