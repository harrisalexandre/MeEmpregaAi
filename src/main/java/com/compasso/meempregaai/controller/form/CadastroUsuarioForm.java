package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class CadastroUsuarioForm {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;

    public Boolean emailNaoUsado(EmpregadoRepository empregadoRepository, EmpregadorRepository empregadorRepository, AdminRepository adminRepository){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findByEmail(email));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findByEmail(email));
        Optional<Admin> adminOptional = Optional.ofNullable(adminRepository.findByEmail(email));

        if (optionalEmpregado.isPresent() || optionalEmpregador.isPresent() || adminOptional.isPresent()){
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
