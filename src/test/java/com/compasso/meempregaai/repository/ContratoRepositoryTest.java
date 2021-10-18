package com.compasso.meempregaai.repository;


import com.compasso.meempregaai.modelo.Contrato;
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

public class ContratoRepositoryTest {

    @Autowired
    private ContratoRepository contratoRepository;

    @Test
    public void retornaContratoPorId(){
        long contratoId = 1;
        Contrato contrato = contratoRepository.findById(contratoId);
        Assertions.assertNotNull(contrato);
        Assertions.assertEquals(contrato.getId(), contratoId);
    }
    @Test
    public void naoRetornaContratoPorId(){
        long contratoId = 99999;
        Contrato contrato = contratoRepository.findById(contratoId);
        Assertions.assertNull(contrato);
    }
}
