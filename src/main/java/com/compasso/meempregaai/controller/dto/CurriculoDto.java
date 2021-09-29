package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Curriculo;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CurriculoDto {
    private long id;
    private String nome;
    private String email;
    private long contato;
    private String endereco;
    private Date dataNascimento;
    private String experienciaProfissional;
    private String escolaridade;
    private String conhecimentoHabilidades;

    public CurriculoDto(Curriculo curriculo){
        this.id = curriculo.getId();
        this.nome = curriculo.getNome();
        this.email = curriculo.getEmail();
        this.contato = curriculo.getContato();
        this.endereco = curriculo.getEndereco();
        this.dataNascimento = curriculo.getDataNascimento();
        this.experienciaProfissional = curriculo.getExperienciaProfissional();
        this.escolaridade = curriculo.getEscolaridade();
        this.conhecimentoHabilidades = curriculo.getConhecimentoHabilidades();
    }

    public static List<CurriculoDto> converter(List<Curriculo>curriculos){
        return curriculos.stream().map(CurriculoDto::new).collect(Collectors.toList());
    }

    public long getId() {return id;}

    public String getNome() {return nome;}

    public String getEmail() {return email;}

    public long getContato() {return contato;}

    public String getEndereco() {return endereco;}

    public Date getDataNascimento() {return dataNascimento;}

    public String getExperienciaProfissional() {return experienciaProfissional;}

    public String getEscolaridade() {return escolaridade;}

    public String getConhecimentoHabilidades() {return conhecimentoHabilidades;}
}

