package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BuscaEmpregadoForm {
    public Specification<Empregado> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicados = new ArrayList<>();
            return builder.and(predicados.toArray(new Predicate[0]));
        };
    }
}
