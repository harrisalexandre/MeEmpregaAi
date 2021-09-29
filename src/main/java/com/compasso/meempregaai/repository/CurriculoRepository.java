package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Curriculo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurriculoRepository extends PagingAndSortingRepository<Curriculo, String>, JpaSpecificationExecutor<Curriculo> {

    Curriculo findById(Long id);

    void deleteById(Long id);
}
