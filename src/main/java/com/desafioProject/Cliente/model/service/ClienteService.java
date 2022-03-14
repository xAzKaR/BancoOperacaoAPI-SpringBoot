package com.desafioProject.Cliente.model.service;

import com.desafioProject.Cliente.api.dto.request.ClienteDto;

import java.util.List;

public interface ClienteService {

    ClienteDto salvar(ClienteDto clienteDto);

    void deleteById(Long id);

    ClienteDto findById(Long id);

    ClienteDto localizarCpf(String cpf);

    ClienteDto localizarCnpj(String cnpj);

    ClienteDto atualizar(ClienteDto clienteDto, Long id);

    ClienteDto atualizar2(ClienteDto clienteDto, Long id);

    List<ClienteDto> listar();

}