package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Perfil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PerfilRepository extends PagingAndSortingRepository<Perfil, String>, JpaSpecificationExecutor<Perfil> {
    Perfil findById(Long id);
}
