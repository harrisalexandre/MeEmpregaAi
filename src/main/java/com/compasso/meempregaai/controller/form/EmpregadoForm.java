package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class EmpregadoForm {

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotNull @Past
    private LocalDate dataNascimento;
    @Email
    private String email;
    @NotBlank
    private String senha;

    public Empregado converter(EmpregadoForm empregadoForm) {
        return new Empregado(nome, cpf, dataNascimento, email, new BCryptPasswordEncoder().encode(senha));
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {this.email = email;}

    public void setSenha(String senha) {this.senha = senha;}
}
