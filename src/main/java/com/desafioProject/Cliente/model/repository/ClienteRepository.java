package com.desafioProject.Cliente.model.repository;

import com.desafioProject.Cliente.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    Cliente findBydocumento(String documento);
    boolean existsBydocumento(String documento);

}