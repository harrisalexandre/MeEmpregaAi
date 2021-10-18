package com.compasso.meempregaai.repository;


import com.compasso.meempregaai.modelo.Curriculo;
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

public class CurriculoRepositoryTest {

    @Autowired
    private CurriculoRepository curriculoRepository;

    @Test
    public void retornaCurriculoPorId(){
        long curriculoId = 1;
        Curriculo curriculo = curriculoRepository.findById(curriculoId);
        Assertions.assertNotNull(curriculo);
        Assertions.assertEquals(curriculo.getId(),curriculoId);
    }

    @Test
    public void naoRetornaCurriculoPorId(){
        long curriculoId = 99999;
        Curriculo curriculo = curriculoRepository.findById(curriculoId);
        Assertions.assertNull(curriculo);
    }
}
