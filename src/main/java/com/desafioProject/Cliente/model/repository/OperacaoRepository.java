package com.desafioProject.Cliente.model.repository;

import com.desafioProject.Cliente.model.entity.Operacao;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoRepository extends PagingAndSortingRepository<Operacao, Long> {

}