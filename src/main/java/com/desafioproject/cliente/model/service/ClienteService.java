package com.desafioproject.cliente.model.service;

import com.desafioproject.cliente.viewer.dto.request.ClienteDto;
import com.desafioproject.cliente.viewer.dto.response.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse salvar(ClienteDto clienteDto);

    void deleteById(Long id);

    ClienteDto findById(Long id);

    ClienteDto localizarCpf(String cpf);

    ClienteDto localizarCnpj(String cnpj);

    ClienteDto atualizar(ClienteDto clienteDto, Long id);

    ClienteDto atualizar2(ClienteDto clienteDto, Long id);

    List<ClienteDto> listar();

}