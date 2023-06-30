package com.desafioproject.cliente.model.service;
import com.desafioproject.cliente.viewer.dto.request.ContaDto;
import com.desafioproject.cliente.viewer.dto.response.ContaResponse;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;
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