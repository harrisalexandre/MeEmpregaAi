package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Vaga;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VagaRepository extends PagingAndSortingRepository<Vaga, String>, JpaSpecificationExecutor<Vaga>{
    Vaga findById(Long id);

    List<Vaga> findByEmpregador(Empregador empregador);
}
