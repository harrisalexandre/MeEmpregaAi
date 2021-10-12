package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Vaga;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BuscaVagaForm {

    private Boolean ativa;
    private Long empregador;

    public Specification<Vaga> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicados = new ArrayList<>();
            if(ativa != null){
                Path<String> campoAtiva = root.<String>get("ativa");
                Predicate predicadoAtiva = builder.equal(campoAtiva, ativa);
                predicados.add(predicadoAtiva);
            }
            if(empregador!= null){
                Path<String> campoEmpregador = root.<String>get("empregador");
                Predicate predicadoEmpregador = builder.equal(campoEmpregador, empregador);
                predicados.add(predicadoEmpregador);
            }

            return builder.and(predicados.toArray(new Predicate[0]));
        };
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public void setEmpregador(Long empregador) {
        this.empregador = empregador;
    }
}
