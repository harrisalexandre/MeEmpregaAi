package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Vaga;

import java.util.List;
import java.util.stream.Collectors;

public class VagaDto {
    private Long id;
    private String nome;
    private String nomeEmpregador;
    private String descricao;
    private List<Empregado> empregados;

    public VagaDto(Vaga vaga) {
        this.id = vaga.getId();
        this.nome = vaga.getNome();
        this.nomeEmpregador = vaga.getEmpregador().getNome();
        this.descricao = vaga.getDescricao();
        this.empregados = vaga.getEmpregados();
    }

    public static List<VagaDto> converter(List<Vaga> vagas) {
        return vagas.stream().map(VagaDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeEmpregador() {
        return nomeEmpregador;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Empregado> getEmpregados() {
        return empregados;
    }
}





