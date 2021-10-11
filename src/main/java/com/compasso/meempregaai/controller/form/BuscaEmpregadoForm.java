package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BuscaEmpregadoForm {
    private Boolean ativo;

    public Specification<Empregado> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicados = new ArrayList<>();

            if(ativo != null){
                Path<String> campoAtivo = root.<String>get("ativo");
                Predicate predicadoAtivo = builder.equal(campoAtivo, ativo);
                predicados.add(predicadoAtivo);
            }

            return builder.and(predicados.toArray(new Predicate[0]));
        };
    }
}
