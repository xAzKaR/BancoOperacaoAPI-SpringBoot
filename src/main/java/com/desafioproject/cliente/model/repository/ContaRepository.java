package com.desafioproject.cliente.model.repository;

import com.desafioproject.cliente.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {
    Conta findBynumeroDaConta(String numeroDaConta);
    boolean existsBynumeroDaConta(String numeroDaConta);
    void deleteContaByNumeroDaConta(String numeroDaConta);
}