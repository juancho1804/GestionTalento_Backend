package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.EvidenciaMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.Evidencia;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ActividadRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.EvidenciaRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvidenciaServiceImpl implements IEvidenciaService{

    @Autowired
    private ManejoArchivoService manejoArchivoService;

    @Autowired
    private EvidenciaRepository evidenciaRepository;

    @Autowired
    private ActividadRepository actividadRepository;


    private EvidenciaMapper evidenciaMapper = new EvidenciaMapper();


    @Override
    public EvidenciaResponseDTO crearEvidencia(EvidenciaRequestDTO evidenciaRequestDTO, MultipartFile file) throws IOException{

        //Validar que algún campo no este vacío
        if(evidenciaRequestDTO.getIdActividad()==null || file==null){
            throw new RuntimeException("Ningún campo puede estar vacio");
        }

        //Buscar la actividad
        Actividad actividad = actividadRepository.findById(evidenciaRequestDTO.getIdActividad()).orElse(null);
        //Validar que la actividad se encuentre
        if(actividad==null){
            throw new RuntimeException("Actividad no encontrada");
        }

        //Guardar evidencia
        String url = manejoArchivoService.saveFile(file);

        if(url==null){
            throw new RuntimeException("Error al guardar evidencia");
        }

        Evidencia evidencia = Evidencia.builder().rutaArchivo(url).actividad(actividad).build();
        return evidenciaMapper.toResponse(evidenciaRepository.save(evidencia));
    }

    @Override
    public EvidenciaResponseDTO actualizarEvidencia(Long id, EvidenciaRequestDTO evidenciaRequestDTO) {
        return null;
    }

    @Override
    public Boolean eliminarEvidencia(Long id) {
        Evidencia evidencia = evidenciaRepository.findById(id).orElse(null);
        if(evidencia==null){
            throw new RuntimeException("Evidencia no encontrada");
        }
        manejoArchivoService.deleteFile(evidencia.getRutaArchivo());
        evidenciaRepository.delete(evidencia);
        return true;
    }

    @Override
    public EvidenciaResponseDTO getEvidencia(Long id) {
        Evidencia evidencia = evidenciaRepository.findById(id).orElse(null);
        if(evidencia==null){
            throw new RuntimeException("Evidencia no encontrada");
        }

        return evidenciaMapper.toResponse(evidencia);
    }

    @Override
    public List<EvidenciaResponseDTO> getEvidencias() {
        List<Evidencia> evidencias = evidenciaRepository.findAll();

        return evidencias.stream()
                .map(evidenciaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
