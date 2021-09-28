package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregador;
import javax.validation.constraints.NotBlank;

public class EmpregadorForm {

    @NotBlank
    private String nome;
    @NotBlank
    private String empresa;
    @NotBlank
    private String cnpj;

    public Empregador converter(EmpregadorForm empregadorForm) {
        return new Empregador(nome, empresa, cnpj);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}