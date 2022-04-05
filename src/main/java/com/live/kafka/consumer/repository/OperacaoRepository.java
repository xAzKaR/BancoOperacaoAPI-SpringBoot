package com.live.kafka.consumer.repository;

import com.live.kafka.consumer.dto.Operacao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoRepository extends PagingAndSortingRepository<Operacao, Long> {
}