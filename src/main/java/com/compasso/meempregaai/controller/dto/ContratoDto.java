package com.compasso.meempregaai.controller.dto;

import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Contrato;
import com.compasso.meempregaai.modelo.Empregador;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContratoDto {

    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean ativo;
    private EmpregadorDto empregador;
    private EmpregadoDto empregado;

    public ContratoDto(Contrato contrato) {
        this.id = contrato.getId();
        this.dataInicio = contrato.getDataInicio();
        this.dataFinal = contrato.getDataFinal();
        this.ativo = contrato.isAtivo();
        this.empregador = new EmpregadorDto(contrato.getEmpregador());
        this.empregado = new EmpregadoDto(contrato.getEmpregado());
    }

    public static List<ContratoDto> converter(List<Contrato> contratos) {
        return contratos.stream().map(ContratoDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getEmpregador() {
        return empregador.getNome();
    }

    public String getEmpregado() {
        return empregado.getNome();
    }
}
