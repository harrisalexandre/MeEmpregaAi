package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.repository.CurriculoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.sql.Date;

public class CurriculoForm {
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private long contato;
    @NotBlank
    private String endereco;
    @NotBlank @Past
    private Date dataNascimento;
    @NotBlank
    private String experienciaProfissional;
    @NotBlank
    private String escolaridade;
    @NotBlank
    private String conhecimentoHabilidades;

    public Curriculo converter(CurriculoForm curriculoForm){ return new Curriculo(nome,email,contato,endereco,dataNascimento,experienciaProfissional,escolaridade,conhecimentoHabilidades);}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContato(long contato) {
        this.contato = contato;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setExperienciaProfissional(String experienciaProfissional) {
        this.experienciaProfissional = experienciaProfissional;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public void setConhecimentoHabilidades(String conhecimentoHabilidades) {
        this.conhecimentoHabilidades = conhecimentoHabilidades;
    }

    public void atualizar(Curriculo curriculo) {

        curriculo.setNome(nome);
        curriculo.setEmail(email);
        curriculo.setContato(contato);
        curriculo.setEndereco(endereco);
        curriculo.setDataNascimento(dataNascimento);
        curriculo.setExperienciaProfissional(experienciaProfissional);
        curriculo.setEscolaridade(escolaridade);
        curriculo.setConhecimentoHabilidades(conhecimentoHabilidades);
    }
}
