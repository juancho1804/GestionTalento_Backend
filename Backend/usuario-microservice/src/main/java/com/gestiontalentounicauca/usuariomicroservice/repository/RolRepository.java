package com.gestiontalentounicauca.usuariomicroservice.repository;

import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import com.gestiontalentounicauca.usuariomicroservice.model.RolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolRepository extends JpaRepository<RolModel,Long> {
    boolean existsByRol(EnumRol rol);
    RolModel getRolModelByRol(EnumRol rol);
    Set<RolModel> findAllByRolIn(Set<EnumRol> roles);
}
