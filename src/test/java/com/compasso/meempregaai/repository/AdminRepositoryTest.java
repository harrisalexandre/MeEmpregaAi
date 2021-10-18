package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Admin;
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

public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void retornaAdminPorId(){
        long adminId = 1;
        Admin admin = adminRepository.findById(adminId);
        Assertions.assertNotNull(admin);
        Assertions.assertEquals(admin.getId(), adminId);
    }

    @Test
    public void naoRetornaAdminPorId(){
        long adminId = 999999;
        Admin admin = adminRepository.findById(adminId);
        Assertions.assertNull(admin);
    }

    @Test
    public void retornaAdminPorEmail(){
        String adminEmail = "admin1@gmail.com";
        Admin admin = adminRepository.findByEmail(adminEmail);
        Assertions.assertNotNull(admin);
        Assertions.assertEquals(admin.getEmail(), adminEmail);
    }
    @Test
    public void naoRetornaAdminPorEmail(){
        String adminEmail = "admin99999@gmail.com";
        Admin admin = adminRepository.findByEmail(adminEmail);
        Assertions.assertNotNull(admin);
    }

}
