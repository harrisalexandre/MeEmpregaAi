package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Empregador;

import java.util.List;
import java.util.stream.Collectors;

public class EmpregadorDto {

    private long id;
    private String nome;
    private String empresa;
    private String cnpj;
    private boolean ativo;

    public EmpregadorDto(Empregador empregador) {
        this.id = empregador.getId();
        this.nome = empregador.getNome();
        this.empresa = empregador.getEmpresa();
        this.cnpj = empregador.getCnpj();
        this.ativo = empregador.isAtivo();
    }

    public static List<EmpregadorDto> converter(List<Empregador> associados) {
        return associados.stream().map(EmpregadorDto::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public boolean isAtivo() {return ativo;}
}
