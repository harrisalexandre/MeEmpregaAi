package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.repository.CurriculoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class AtualizaCurriculoForm {

    private String contato;
    private String endereco;
    private String experienciaProfissional;
    private String escolaridade;
    private String conhecimentoHabilidades;

    public Curriculo atualizar(Long id, CurriculoRepository curriculoRepository){
        Optional<Curriculo> optionalCurriculo = Optional.ofNullable(curriculoRepository.findById(id));
        if (optionalCurriculo.isPresent()){
            Curriculo curriculo = optionalCurriculo.get();
            curriculo.setContato(contato);
            curriculo.setEndereco(endereco);
            curriculo.setExperienciaProfissional(experienciaProfissional);
            curriculo.setEscolaridade(escolaridade);
            curriculo.setConhecimentoHabilidades(conhecimentoHabilidades);
            return curriculo;
        }
        throw new IllegalArgumentException("Dados inválidos");
    }

    public Curriculo resetar(long id, CurriculoRepository curriculoRepository) throws IllegalArgumentException {
        Optional<Curriculo> optionalCurriculo = Optional.ofNullable(curriculoRepository.findById(id));
        if (optionalCurriculo.isPresent()){
            Curriculo curriculo = optionalCurriculo.get();
            curriculo.setContato("");
            curriculo.setEndereco("");
            curriculo.setExperienciaProfissional("");
            curriculo.setEscolaridade("");
            curriculo.setConhecimentoHabilidades("");
            return curriculo;
        }
        throw new IllegalArgumentException("Dados inválidos");
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setExperienciaProfissional(String experienciaProfissional) {
        this.experienciaProfissional = experienciaProfissional;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public void setConhecimentoHabilidades(String conhecimentoHabilidades) {
        this.conhecimentoHabilidades = conhecimentoHabilidades;
    }
}
