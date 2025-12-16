package com.gestiontalentounicauca.actividadesmicroservice.repository;

import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {
}
