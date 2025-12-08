package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.MotivoPagoMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.MotivoPago;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoMotivoPago;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoPlan;
import com.gestiontalentounicauca.actividadesmicroservice.repository.ActividadRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.MotivoPagoRepository;
import com.gestiontalentounicauca.actividadesmicroservice.repository.TipoMotivoPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivoPagoServiceImpl implements IMotivoPagoService {

    @Autowired
    MotivoPagoRepository motivoPagoRepository;

    MotivoPagoMapper motivoPagoMapper = new MotivoPagoMapper();

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private TipoMotivoPagoRepository tipoMotivoPagoRepository;

    @Override
    public MotivoPagoResponseDTO crearMotivoPago(MotivoPagoRequestDTO motivoPagoRequestDTO) {

        //Validar que el monto sea mayor a 0
        if(motivoPagoRequestDTO.getMonto()<1){
            throw new RuntimeException("El monto no puede ser inferior a 1");
        }


        //Buscar actividad
        Actividad actividad = actividadRepository.findById(motivoPagoRequestDTO.getIdActividad()).orElseThrow(()->new EntityNotFoundException("Actividad no encontrada"));

        //Validar que la actividad sea de capacitacion o bienestar
        if (!(actividad.getPlan().getTipoPlan().equals(TipoPlan.CAPACITACION) || actividad.getPlan().getTipoPlan().equals(TipoPlan.BIENESTAR))) {
            throw new RuntimeException("Solo las actividades de capacitación o bienestar pueden tener encuestas");
        }

        TipoMotivoPago tipoMotivoPago;
        //Si el id del tipo se envia, se busca
        if(motivoPagoRequestDTO.getIdTipoMotivoPago()!=null){
            tipoMotivoPago = tipoMotivoPagoRepository.findById(motivoPagoRequestDTO.getIdTipoMotivoPago()).orElseThrow(()->new EntityNotFoundException("TipoMotivoPago no encontrado"));
        }else{ //En caso contrario, se busca por nombre
            String nombreTipoMotivoPago = motivoPagoRequestDTO.getNombreTipoMotivoPago().trim();
            tipoMotivoPago = tipoMotivoPagoRepository.findByNombreIgnoreCase(nombreTipoMotivoPago);

            //Si no se encuentra por nombre, entonces se crea un nuevo tipomotivopago, y se guarda en la base de datos
            if(tipoMotivoPago==null){
                tipoMotivoPago = new TipoMotivoPago();
                tipoMotivoPago.setNombre(nombreTipoMotivoPago);
                tipoMotivoPagoRepository.save(tipoMotivoPago);
            }
        }

        if(motivoPagoRepository.findByActividadIdAndDominioId(actividad.getId(), tipoMotivoPago.getId()).isPresent()){
            throw new RuntimeException("Ya existe un motivo de pago con esta actividad");
        }

        MotivoPago motivoPago = motivoPagoRepository.save(motivoPagoMapper.ToEntity(motivoPagoRequestDTO, tipoMotivoPago, actividad));

        return motivoPagoMapper.toResponseDTO(motivoPago);
    }

    @Override
    public MotivoPagoResponseDTO actualizarMotivoPago(Long idMotivoPago, MotivoPagoRequestDTO dto) {

        MotivoPago motivoPago = motivoPagoRepository.findById(idMotivoPago)
                .orElseThrow(() -> new EntityNotFoundException("MotivoPago no encontrado"));

        // Validar monto
        if (dto.getMonto() < 1) {
            throw new RuntimeException("El monto no puede ser inferior a 1");
        }

        // Cambiar actividad si viene en el DTO
        if (dto.getIdActividad() != null) {
            Actividad nuevaActividad = actividadRepository.findById(dto.getIdActividad())
                    .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

            if (!(nuevaActividad.getPlan().getTipoPlan().equals(TipoPlan.CAPACITACION)
                    || nuevaActividad.getPlan().getTipoPlan().equals(TipoPlan.BIENESTAR))) {
                throw new RuntimeException("Solo las actividades de capacitación o bienestar pueden tener encuestas");
            }

            motivoPago.setActividad(nuevaActividad);
        }

        // Cambiar tipoMotivoPago si viene en el DTO
        if (dto.getIdTipoMotivoPago() != null || dto.getNombreTipoMotivoPago() != null) {

            TipoMotivoPago tipo;

            if (dto.getIdTipoMotivoPago() != null) {
                tipo = tipoMotivoPagoRepository.findById(dto.getIdTipoMotivoPago())
                        .orElseThrow(() -> new EntityNotFoundException("TipoMotivoPago no encontrado"));
            } else {
                String nombre = dto.getNombreTipoMotivoPago().trim();
                tipo = tipoMotivoPagoRepository.findByNombreIgnoreCase(nombre);

                if (tipo == null) {
                    tipo = new TipoMotivoPago();
                    tipo.setNombre(nombre);
                    tipoMotivoPagoRepository.save(tipo);
                }
            }

            motivoPago.setDominio(tipo);
        }

        // Actualizar monto
        motivoPago.setMontoAsignado(dto.getMonto());

        motivoPagoRepository.save(motivoPago);
        return motivoPagoMapper.toResponseDTO(motivoPago);
    }
    @Override
    public Boolean eliminarMotivoPago(Long idMotivoPago) {
        MotivoPago motivoPago = motivoPagoRepository.findById(idMotivoPago)
                .orElseThrow(() -> new EntityNotFoundException("MotivoPago no encontrado"));

        motivoPagoRepository.delete(motivoPago);
        return true;
    }

    @Override
    public List<MotivoPagoResponseDTO> listarMotivoPagos() {
        return motivoPagoRepository.findAll()
                .stream()
                .map(motivoPagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public MotivoPagoResponseDTO encontrarPorId(Long idMotivoPago) {
        MotivoPago motivoPago = motivoPagoRepository.findById(idMotivoPago)
                .orElseThrow(() -> new EntityNotFoundException("MotivoPago no encontrado"));

        return motivoPagoMapper.toResponseDTO(motivoPago);
    }
}
