package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregador;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpregadorRepository extends PagingAndSortingRepository<Empregador, String>, JpaSpecificationExecutor<Empregador> {
    Empregador findById(Long id);
    Empregador findByNome(String nomeEmpregador);
    Empregador findByEmail(String userName);
}
