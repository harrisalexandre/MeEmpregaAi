package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "curriculo")
public class Curriculo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String contato;
    private String endereco;
    private String experienciaProfissional;
    private String escolaridade;
    private String conhecimentoHabilidades;

    @OneToOne
    private Empregado empregado;

    public Curriculo(Empregado empregado){
        this.empregado = empregado;
        this.nome = empregado.getNome();
        this.email = empregado.getEmail();
        this.dataNascimento = empregado.getDataNascimento();
        this.contato = "";
        this.endereco = "";
        this.experienciaProfissional = "";
        this.escolaridade = "";
        this.conhecimentoHabilidades = "";

    }
    public Curriculo(){}

    public Curriculo(String contato, String endereco, String experienciaProfissional, String escolaridade, String conhecimentoHabilidades) {
    }

    public long getId() { return id;}

    public void setId(long id) { this.id = id;}

    public String getNome() { return nome;}

    public void setNome(String nome) { this.nome = nome;}

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public String getContato() { return contato;}

    public void setContato(String contato) { this.contato = contato;}

    public String getEndereco() { return endereco;}

    public void setEndereco(String endereco) { this.endereco = endereco;}

    public LocalDate getDataNascimento() { return dataNascimento;}

    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento;}

    public String getExperienciaProfissional() { return experienciaProfissional;}

    public void setExperienciaProfissional(String experienciaProfissional) { this.experienciaProfissional = experienciaProfissional;}

    public String getEscolaridade() { return escolaridade;}

    public void setEscolaridade(String escolaridade) { this.escolaridade = escolaridade;}

    public String getConhecimentoHabilidades() { return conhecimentoHabilidades;}

    public void setConhecimentoHabilidades(String conhecimentoHabilidades) { this.conhecimentoHabilidades = conhecimentoHabilidades;}

    public Empregado getEmpregado() { return empregado;}

    public void setEmpregado(Empregado empregado) { this.empregado = empregado;}


}
