package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregador;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmpregadorForm {

    @NotBlank
    private String nome;
    @NotBlank
    private String empresa;
    @NotBlank
    private String cnpj;
    @Email
    private String email;
    @NotBlank
    private String senha;

    public Empregador converter(EmpregadorForm empregadorForm) {
        return new Empregador(nome, empresa, cnpj, email, new BCryptPasswordEncoder().encode(senha));
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmail(String email) {this.email = email;}

    public void setSenha(String senha) {this.senha = senha;}
}
