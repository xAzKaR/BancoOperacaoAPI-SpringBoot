package com.desafioProject.Cliente.viewer.mappers;

import com.desafioProject.Cliente.viewer.dto.request.ContaDto;
import com.desafioProject.Cliente.viewer.dto.response.ContaResponse;
import com.desafioProject.Cliente.model.entity.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperConta {
    ContaDto toDto(Conta conta);

    Conta toModel(ContaDto contaDto);

    void atualizar(ContaDto contaDto, @MappingTarget Conta conta);

    ContaResponse toResponse(Conta conta);
}
