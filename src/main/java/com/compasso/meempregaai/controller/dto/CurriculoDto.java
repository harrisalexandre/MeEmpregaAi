package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Curriculo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CurriculoDto {
    private String nome;
    private String email;
    private String contato;
    private String endereco;
    private LocalDate dataNascimento;
    private String experienciaProfissional;
    private String escolaridade;
    private String conhecimentoHabilidades;

    public CurriculoDto(Curriculo curriculo){
        this.nome = curriculo.getNome();
        this.email = curriculo.getEmail();
        this.contato = curriculo.getContato();
        this.endereco = curriculo.getEndereco();
        this.dataNascimento = curriculo.getDataNascimento();
        this.experienciaProfissional = curriculo.getExperienciaProfissional();
        this.escolaridade = curriculo.getEscolaridade();
        this.conhecimentoHabilidades = curriculo.getConhecimentoHabilidades();
    }

    public String getNome() {return nome;}

    public String getEmail() {return email;}

    public String getContato() {return contato;}

    public String getEndereco() {return endereco;}

    public LocalDate getDataNascimento() {return dataNascimento;}

    public String getExperienciaProfissional() {return experienciaProfissional;}

    public String getEscolaridade() {return escolaridade;}

    public String getConhecimentoHabilidades() {return conhecimentoHabilidades;}
}

