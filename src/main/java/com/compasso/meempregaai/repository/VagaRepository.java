package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Vaga;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VagaRepository extends PagingAndSortingRepository<Vaga, Long>, JpaSpecificationExecutor<Vaga>{

}
