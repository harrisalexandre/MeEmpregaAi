package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregador;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmpregadorRepository extends PagingAndSortingRepository<Empregador, String>, JpaSpecificationExecutor<Empregador> {
    Empregador findById(Long id);
    Empregador findByEmail(String userName);
    List<Empregador> findAllByAtivoIsTrue(Pageable pageable);
}
