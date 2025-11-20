package com.gestiontalentounicauca.actividadesmicroservice.repository;

import com.gestiontalentounicauca.actividadesmicroservice.model.MotivoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotivoPagoRepository extends JpaRepository<MotivoPago, Long> {
    Optional<MotivoPago> findByActividadIdAndDominioId(Long idActividad, Long idDominio);
}
