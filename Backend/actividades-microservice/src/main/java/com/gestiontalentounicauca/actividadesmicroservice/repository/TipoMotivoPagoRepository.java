package com.gestiontalentounicauca.actividadesmicroservice.repository;

import com.gestiontalentounicauca.actividadesmicroservice.model.TipoMotivoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMotivoPagoRepository extends JpaRepository<TipoMotivoPago,Long> {

    TipoMotivoPago findByNombreIgnoreCase(String nombre);
}
