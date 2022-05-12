package com.desafioProject.Cliente.model.service;

import com.desafioProject.Cliente.viewer.dto.request.OperacaoDto;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioProject.Cliente.viewer.exception.OperacaoNaoCompletadaException;

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