package com.gestiontalentounicauca.actividadesmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ActividadesMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActividadesMicroserviceApplication.class, args);
    }

}
