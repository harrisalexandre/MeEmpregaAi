package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmpregadoRepositoryTest {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Test
    public void retornaEmpregadoPorId(){
        long empregadoId = 1;
        Empregado empregado = empregadoRepository.findById(empregadoId);
        Assertions.assertNotNull(empregado);
        Assertions.assertEquals(empregado.getId(), empregadoId);
    }

    @Test
    public void naoRetornaEmpregadoPorId(){
        long empregadoId = 99999;
        Empregado empregado = empregadoRepository.findById(empregadoId);
        Assertions.assertNull(empregado);

    }

}