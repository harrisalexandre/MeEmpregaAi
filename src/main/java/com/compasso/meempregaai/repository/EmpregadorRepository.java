package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregador;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpregadorRepository extends PagingAndSortingRepository<Empregador, Long>, JpaSpecificationExecutor<Empregador> {
}
