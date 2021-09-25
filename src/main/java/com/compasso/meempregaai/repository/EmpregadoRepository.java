package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpregadoRepository extends PagingAndSortingRepository<Empregado, Long>, JpaSpecificationExecutor<Empregado>{
}
