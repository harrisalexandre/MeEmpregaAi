package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregado;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface EmpregadoRepository  extends PagingAndSortingRepository<Empregado, String>, JpaSpecificationExecutor<Empregado> {
    Empregado findById(Long id);
    Empregado findByEmail(String userName);
    List<Empregado> findAllByAtivoIsTrue(Pageable pageable);
}
