package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Perfil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")

public class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @Test
    public void retornaPerfilPorId(){
        long perfilId = 1;
        Perfil perfil = perfilRepository.findById(perfilId);
        Assertions.assertNotNull(perfil);
        Assertions.assertEquals(perfil.getId(),perfilId);
    }

    @Test
    public void naoRetornaPerfilPorId(){
        long perfilId = 99999;
        Perfil perfil = perfilRepository.findById(perfilId);
        Assertions.assertNull(perfil);
    }
}
