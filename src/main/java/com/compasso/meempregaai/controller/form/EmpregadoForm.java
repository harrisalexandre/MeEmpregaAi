package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class EmpregadoForm extends CadastroUsuarioForm {

    @NotBlank
    private String cpf;
    @NotNull @Past
    private LocalDate dataNascimento;


    public Empregado converter(EmpregadoForm empregadoForm) {
        return new Empregado(getNome(), cpf, dataNascimento, getEmail(), new BCryptPasswordEncoder().encode(getSenha()));
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
