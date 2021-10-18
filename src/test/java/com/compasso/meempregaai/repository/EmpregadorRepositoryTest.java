package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregador;
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

public class EmpregadorRepositoryTest {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Test
    public void retornaEmpregadorPorId(){

        long empregadorId = 1;
        Empregador empregador = empregadorRepository.findById(empregadorId);
        Assertions.assertNotNull(empregador);
        Assertions.assertEquals(empregador.getId(),empregadorId);
    }

    @Test
    public void naoRetornaEmpregadorPorId(){
        long empregadorId = 99999;
        Empregador empregador = empregadorRepository.findById(empregadorId);
        Assertions.assertNull(empregador);
    }

    @Test
    public void retornaEmpregadorPorEmail(){
        String empregadorEmail = "empregador1@gmail.com";
        Empregador empregador = empregadorRepository.findByEmail(empregadorEmail);
        Assertions.assertNotNull(empregador);
        Assertions.assertEquals(empregador.getEmail(), empregadorEmail);
    }

    @Test
    public void naoRetornaEmpregadorPorEmail(){
        String empregadorEmail = "empregador99999@gmail.com";
        Empregador empregador = empregadorRepository.findByEmail(empregadorEmail);
        Assertions.assertNotNull(empregador);
    }

}
