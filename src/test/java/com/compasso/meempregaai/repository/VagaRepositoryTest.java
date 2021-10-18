package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Vaga;
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

public class VagaRepositoryTest {

    @Autowired
    private VagaRepository vagaRepository;

    @Test
    public void retornaVagaPorId(){
        long vagaId = 1;
        Vaga vaga = vagaRepository.findById(vagaId);
        Assertions.assertNotNull(vaga);
        Assertions.assertEquals(vaga.getId(),vagaId);
    }

    @Test
    public void naoRetornaVagaPorId(){
        long vagaId = 99999;
        Vaga vaga = vagaRepository.findById(vagaId);
        Assertions.assertNull(vaga);
    }
}
