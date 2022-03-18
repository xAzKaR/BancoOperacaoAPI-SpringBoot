package com.desafioProject.Cliente.model.service;
import com.desafioProject.Cliente.api.dto.request.ContaDto;
import com.desafioProject.Cliente.api.dto.response.ContaResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContaService {

    ContaResponse salvar(ContaDto contaDto);

    void deletar(Long id);

    ContaDto localizarId(Long id);

    ContaDto locazalicarConta(String numeroDaConta);

//  Page<ContaDto> findByCondition(ContaDto contaDto, Pageable pageable);

    ContaDto atualizar(ContaDto contaDto, Long id);

    ResponseEntity<ContaDto> atualizar2(ContaDto contaDto, Long id);

    List<ContaDto> listar();

}