package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Vaga;
import com.compasso.meempregaai.repository.EmpregadorRepository;

import javax.validation.constraints.NotBlank;

public class VagaForm {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String nomeEmpregador;

    public Vaga converter(VagaForm vagaForm, EmpregadorRepository empregadorRepository) {
        Empregador empregador = empregadorRepository.findByNome(nomeEmpregador);

        return new Vaga(nome, descricao, empregador);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNomeEmpregador(String nomeEmpregador) {
        this.nomeEmpregador = nomeEmpregador;
    }

}



