package com.compasso.meempregaai.controller.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.compasso.meempregaai.modelo.Admin;

public class AdminDto {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    @Autowired
    public Admin admin;

    public AdminDto(Admin admin) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;

    }

    public static Admin getAdmin() {return getAdmin();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}