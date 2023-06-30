package com.desafioproject.cliente.model.service;

import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface OperacaoService {

    OperacaoDepositoResponse depositar(OperacaoDto operacaoDto) throws OperacaoNaoCompletadaException;

    OperacaoSaqueResponse sacar(OperacaoDto operacaoDto) throws ExecutionException, InterruptedException, TimeoutException;

    OperacaoTransfResponse transferir(OperacaoDto operacaoDto);

    void deletar(Long id);

    OperacaoDto findById(Long id);

    List<OperacaoDto> listar();

}