package com.gestiontalentounicauca.actividadesmicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test") // Activa el perfil test
class ActividadesMicroserviceApplicationTests {

    @Test
    void contextLoads() {
    }

}
