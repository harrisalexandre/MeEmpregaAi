package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.modelo.Contrato;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AdminDto {

    private Long id;
    private String nome;
    private String email;


    public AdminDto(Admin admin) {
        this.id = admin.getId();
        this.nome = admin.getNome();
        this.email = admin.getEmail();
    }

    public static List<AdminDto> converter(List<Admin> admins) {
        return admins.stream().map(AdminDto::new).collect(Collectors.toList());
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


}
