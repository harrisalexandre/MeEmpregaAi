package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "curriculo")
public class Curriculo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private long contato;
    private String endereco;
    private Date dataNascimento;
    private String experienciaProfissional;
    private String escolaridade;
    private String conhecimentoHabilidades;

    public Curriculo(long id, String nome, String email, long contato, String endereco, Date dataNascimento, String experienciaProfissional, String escolaridade, String conhecimentoHabilidades, Empregado empregado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.contato = contato;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.experienciaProfissional = experienciaProfissional;
        this.escolaridade = escolaridade;
        this.conhecimentoHabilidades = conhecimentoHabilidades;
        this.empregado = empregado;
    }
    public Curriculo(){}

    public Curriculo(String nome, String email, long contato, String endereco, Date dataNascimento, String experienciaProfissional, String escolaridade, String conhecimentoHabilidades) {
    }

    public long getId() { return id;}

    public void setId(long id) { this.id = id;}

    public String getNome() { return nome;}

    public void setNome(String nome) { this.nome = nome;}

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public long getContato() { return contato;}

    public void setContato(long contato) { this.contato = contato;}

    public String getEndereco() { return endereco;}

    public void setEndereco(String endereco) { this.endereco = endereco;}

    public Date getDataNascimento() { return dataNascimento;}

    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento;}

    public String getExperienciaProfissional() { return experienciaProfissional;}

    public void setExperienciaProfissional(String experienciaProfissional) { this.experienciaProfissional = experienciaProfissional;}

    public String getEscolaridade() { return escolaridade;}

    public void setEscolaridade(String escolaridade) { this.escolaridade = escolaridade;}

    public String getConhecimentoHabilidades() { return conhecimentoHabilidades;}

    public void setConhecimentoHabilidades(String conhecimentoHabilidades) { this.conhecimentoHabilidades = conhecimentoHabilidades;}

    public Empregado getEmpregado() { return empregado;}

    public void setEmpregado(Empregado empregado) { this.empregado = empregado;}

    @OneToOne
    @JoinColumn(name = "empregado_id")
    private Empregado empregado;

}
