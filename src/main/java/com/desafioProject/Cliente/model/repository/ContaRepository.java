package com.desafioProject.Cliente.model.repository;

import com.desafioProject.Cliente.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {
    Conta findBynumeroDaConta(String numeroDaConta);
    boolean existsBynumeroDaConta(String numeroDaConta);
    void deleteContaByNumeroDaConta(String numeroDaConta);
}