package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.EncuestaMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EncuestaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Encuesta;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.repository.EncuestaRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncuestaServiceImpl implements IEncuestaService {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EncuestaMapper encuestaMapper;



    @Override
    public EncuestaResponseDTO crearEncuesta(EncuestaRequestDTO encuestaRequestDTO) {

        //Validar que el participante existe
        Participante participante = participanteRepository.findById(encuestaRequestDTO.getIdParticipante()).orElseThrow(()-> new RuntimeException("Participante no encontrado"));

        //Validar que el participante ya dio su encuesta
        if(encuestaRepository.existsByParticipante_Id(participante.getId())){
            throw new RuntimeException("La encuesta ya existe en el sistema");
        }


        Encuesta encuesta = Encuesta.builder()
                .participante(participante)
                .calificacion(encuestaRequestDTO.getCalificacion())
                .build();


        return encuestaMapper.toResponseDTO(encuestaRepository.save(encuesta));
    }

    @Override
    public EncuestaResponseDTO actualizarEncuesta(Long id, EncuestaRequestDTO encuestaRequestDTO) {

        Encuesta encuesta = encuestaRepository.findById(id).orElseThrow(()-> new RuntimeException("Encuesta no encontrada"));

        if(!encuesta.getParticipante().getId().equals(encuestaRequestDTO.getIdParticipante())){
            encuesta.setParticipante(participanteRepository.findById(encuestaRequestDTO.getIdParticipante()).orElseThrow(()-> new RuntimeException("Participante no encontrado")));
        }
        encuesta.setCalificacion(encuestaRequestDTO.getCalificacion());

        return encuestaMapper.toResponseDTO(encuestaRepository.save(encuesta));
    }

    @Override
    public Boolean eliminarEncuesta(Long id) {
        if(id==null) {
            throw new RuntimeException("El id de la encuesta no puede estar vacio");
        }

        if(!encuestaRepository.existsById(id)) {
            throw new RuntimeException("No se encontro la encuesta");
        }
        encuestaRepository.deleteById(id);

        return true;
    }

    @Override
    public EncuestaResponseDTO getEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id).orElseThrow(()-> new RuntimeException("Encuesta no encontrada"));
        return encuestaMapper.toResponseDTO(encuesta);
    }

    @Override
    public List<EncuestaResponseDTO> getEncuestas() {
        List<Encuesta> encuestas = encuestaRepository.findAll();
        return encuestas.stream().map(encuestaMapper::toResponseDTO).collect(Collectors.toList());
    }
}
