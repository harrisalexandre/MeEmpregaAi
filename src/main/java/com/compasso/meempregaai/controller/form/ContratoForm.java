package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Contrato;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class ContratoForm {


    private LocalDate dataFinal;

    private long empregadoId;

    private long empregadorId;

    public Contrato converter(ContratoForm contratoForm, Empregado empregado, Empregador empregador) {
        return new Contrato(empregado, empregador, dataFinal) ;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public long getEmpregadoId() {
        return empregadoId;
    }

    public void setEmpregadoId(long empregadoId) {
        this.empregadoId = empregadoId;
    }

    public long getEmpregadorId() {
        return empregadorId;
    }

    public void setEmpregadorId(long empregadorId) {
        this.empregadorId = empregadorId;
    }
}
