package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Contrato;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContratoRepository extends PagingAndSortingRepository<Contrato, String>, JpaSpecificationExecutor<Contrato>{
    Contrato findById(Long id);

}
