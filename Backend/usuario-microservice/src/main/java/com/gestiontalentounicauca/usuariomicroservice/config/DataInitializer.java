package com.gestiontalentounicauca.usuariomicroservice.config;

import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import com.gestiontalentounicauca.usuariomicroservice.model.RolModel;
import com.gestiontalentounicauca.usuariomicroservice.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        for(EnumRol rol : EnumRol.values()){
            if(!rolRepository.existsByRol(rol)){
                RolModel rolModel = new RolModel();
                rolModel.setRol(rol);
                rolRepository.save(rolModel);

            }
        }
        System.out.println("Roles insertados exitosamente...");
    }
}
