package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.repository.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class AdminForm extends CadastroUsuarioForm{

    public  Admin atualizar(Long id, AdminRepository adminRepository) {
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findById(id));
        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            admin.setNome(getNome());
            admin.setEmail(getEmail());
            admin.setSenha(new BCryptPasswordEncoder().encode(getSenha()));
            return admin;
        }
        throw new IllegalArgumentException("Dados invalidos");
    }
    public Admin converter(AdminForm adminForm) {
        return new Admin(getNome(), getEmail(), new BCryptPasswordEncoder().encode(getSenha()));
    }


}
