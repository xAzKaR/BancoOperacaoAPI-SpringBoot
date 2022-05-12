package com.desafioProject.Cliente.model.service;
import com.desafioProject.Cliente.viewer.dto.request.ContaDto;
import com.desafioProject.Cliente.viewer.dto.response.ContaResponse;
import com.desafioProject.Cliente.viewer.exception.OperacaoNaoCompletadaException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContaService {

    ContaResponse salvar(ContaDto contaDto) throws InterruptedException, OperacaoNaoCompletadaException;

    void deletar(Long id);

    void deletarNumeroDaConta(String numeroDaConta);

    ContaDto localizarId(Long id);

    ContaDto locazalicarConta(String numeroDaConta);

//  Page<ContaDto> findByCondition(ContaDto contaDto, Pageable pageable);

    ContaDto atualizar(ContaDto contaDto, Long id);

    ResponseEntity<ContaDto> atualizar2(ContaDto contaDto, Long id);

    List<ContaDto> listar();

}