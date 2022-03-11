package com.desafioProject.Cliente.model.service;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;

import java.util.List;

public interface OperacaoService {

    OperacaoDto depositar(OperacaoDto operacaoDto);

    OperacaoDto sacar(OperacaoDto operacaoDto);

    OperacaoDto transferir(OperacaoDto operacaoDto);

    void deletar(Long id);

    OperacaoDto findById(Long id);

    List<OperacaoDto> listar();

}