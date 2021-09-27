package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Empregado;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EmpregadoDto {

    private long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public EmpregadoDto(Empregado empregado) {
        this.id = empregado.getId();
        this.nome = empregado.getNome();
        this.cpf = empregado.getCpf();
        this.dataNascimento = empregado.getDataNascimento();
    }

    public static List<EmpregadoDto> converter(List<Empregado> empregados) {
        return empregados.stream().map(EmpregadoDto::new).collect(Collectors.toList());
    }
}