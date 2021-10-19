package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class EmpregadorForm extends CadastroUsuarioForm{

    @NotBlank
    private String empresa;
    @NotBlank
    private String cnpj;
    public Empregador atualizar(Long id, EmpregadorRepository empregadorRepository) {
        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));
        if (optionalEmpregador.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            empregador.setNome(getNome());
            empregador.setEmpresa(empresa);
            empregador.setCnpj(cnpj);
            empregador.setEmail(getEmail());
            empregador.setSenha(new BCryptPasswordEncoder().encode(getSenha()));
            return empregador;
        }
        throw new IllegalArgumentException("Dados invalidos");
    }

    public Empregador converter(EmpregadorForm empregadorForm) {
        return new Empregador(getNome(), empresa, cnpj, getEmail(), new BCryptPasswordEncoder().encode(getSenha()));
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
