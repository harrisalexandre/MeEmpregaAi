package com.compasso.meempregaai.controller.form;

import com.compasso.meempregaai.modelo.Vaga;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BuscaVagaForm {

    public Specification<Vaga> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicados = new ArrayList<>();
            return builder.and(predicados.toArray(new Predicate[0]));

        };
    }
}
