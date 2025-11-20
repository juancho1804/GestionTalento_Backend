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

        //Buscar actividad
        Actividad actividad = actividadRepository.findById(motivoPagoRequestDTO.getIdActividad()).orElseThrow(()->new EntityNotFoundException("Actividad no encontrada"));

        //Validar que la actividad sea de capacitacion o bienestar
        if (!(actividad.getPlan().getTipoPlan().equals(TipoPlan.CAPACITACION) || actividad.getPlan().getTipoPlan().equals(TipoPlan.BIENESTAR))) {
            throw new RuntimeException("Solo las actividades de capacitación o bienestar pueden tener encuestas");
        }
        MotivoPago motivoPago = motivoPagoRepository.save(motivoPagoMapper.ToEntity(motivoPagoRequestDTO, tipoMotivoPago, actividad));

        return motivoPagoMapper.toResponseDTO(motivoPago);
    }

    @Override
    public MotivoPagoResponseDTO actualizarMotivoPago(Long idMotivoPago, MotivoPagoRequestDTO motivoPagoRequestDTO) {
        return null;
    }

    @Override
    public Boolean eliminarMotivoPago(Long idMotivoPago) {
        return null;
    }

    @Override
    public List<MotivoPagoResponseDTO> listarMotivoPagos() {
        return List.of();
    }

    @Override
    public MotivoPagoResponseDTO encontrarPorId(Long idMotivoPago) {
        return null;
    }
}
