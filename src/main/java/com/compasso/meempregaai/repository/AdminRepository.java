package com.compasso.meempregaai.repository;

import com.compasso.meempregaai.modelo.Admin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminRepository extends PagingAndSortingRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    Admin findById(Long id);
    Admin findByEmail(String userName);
}
