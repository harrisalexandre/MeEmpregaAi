package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Vaga;
import com.compasso.meempregaai.repository.EmpregadorRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VagaForm {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Long empregadorId;

    public Vaga converter(VagaForm vagaForm, EmpregadorRepository empregadorRepository) {
        Empregador empregador = empregadorRepository.findById(empregadorId);

        return new Vaga(nome, descricao, empregador);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEmpregadorId(Long empregadorId) {
        this.empregadorId = empregadorId;
    }

}



