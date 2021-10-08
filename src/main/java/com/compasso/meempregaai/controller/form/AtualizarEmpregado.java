package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class AtualizarEmpregado {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;

    public Empregado atualizar(Long id, EmpregadoRepository empregadoRepository){
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if (optionalEmpregado.isPresent()){
            Empregado empregado = optionalEmpregado.get();
            empregado.setNome(this.nome);
            empregado.setEmail(this.email);
            empregado.setSenha(new BCryptPasswordEncoder().encode(this.senha));
            return empregado;
        }
        throw new IllegalArgumentException("Dados inválidos");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
