package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdminForm {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Admin converter(AdminForm adminForm, AdminRepository adminRepository) {
        return new Admin(nome, email,senha);
    }
}
