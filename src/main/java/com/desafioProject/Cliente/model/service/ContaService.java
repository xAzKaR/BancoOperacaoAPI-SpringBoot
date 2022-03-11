package com.desafioProject.Cliente.model.service;
import com.desafioProject.Cliente.api.dto.request.ContaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContaService {

    ContaDto salvar(ContaDto contaDto);

    void deletar(Long id);

    ContaDto localizarId(Long id);

    ContaDto locazalicarConta(String numeroDaConta);

//  Page<ContaDto> findByCondition(ContaDto contaDto, Pageable pageable);

    ContaDto atualizar(ContaDto contaDto, Long id);

    ResponseEntity<ContaDto> atualizar2(ContaDto contaDto, Long id);

    List<ContaDto> listar();

}