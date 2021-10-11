package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "empregado_empregador")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean ativo;
    @ManyToOne
    private Empregador empregador;
    @ManyToOne
    private Empregado empregado;

    public Contrato(Empregado empregado, Empregador empregador, LocalDate dataFinal) {
        this.empregador = empregador;
        this.empregado = empregado;
        this.ativo = true;
        this.dataInicio = LocalDate.now();
        this.dataFinal = dataFinal;
    }

    public Contrato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Empregador getEmpregador() {
        return empregador;
    }

    public void setEmpregador(Empregador empregador) {
        this.empregador = empregador;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }
}
