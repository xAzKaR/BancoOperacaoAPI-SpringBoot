package com.desafioProject.Cliente.model.service;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;
import com.desafioProject.Cliente.api.dto.response.OperacaoDepositoResponse;
import com.desafioProject.Cliente.api.dto.response.OperacaoSaqueResponse;
import com.desafioProject.Cliente.api.dto.response.OperacaoTransfResponse;

import java.util.List;

public interface OperacaoService {

    OperacaoDepositoResponse depositar(OperacaoDto operacaoDto);

    OperacaoSaqueResponse sacar(OperacaoDto operacaoDto);

    OperacaoTransfResponse transferir(OperacaoDto operacaoDto);

    void deletar(Long id);

    OperacaoDto findById(Long id);

    List<OperacaoDto> listar();

}