package com.gestiontalentounicauca.actividadesmicroservice.repository;

import com.gestiontalentounicauca.actividadesmicroservice.model.MotivoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoPagoRepository extends JpaRepository<MotivoPago, Long> {
}
