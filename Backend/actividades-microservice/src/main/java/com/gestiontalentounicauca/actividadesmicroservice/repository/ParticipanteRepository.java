package com.gestiontalentounicauca.actividadesmicroservice.repository;

import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante,Long> {
}
