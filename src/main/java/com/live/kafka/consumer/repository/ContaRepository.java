package com.live.kafka.consumer.repository;

import com.live.kafka.consumer.dto.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {
    Conta findContaByNumeroDaConta (String numeroDaConta);
}