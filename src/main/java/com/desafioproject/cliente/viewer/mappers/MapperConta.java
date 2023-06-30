package com.desafioproject.cliente.viewer.mappers;

import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.viewer.dto.request.ContaDto;
import com.desafioproject.cliente.viewer.dto.response.ContaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperConta {
    ContaDto toDto(Conta conta);

    Conta toModel(ContaDto contaDto);

    void atualizar(ContaDto contaDto, @MappingTarget Conta conta);

    ContaResponse toResponse(Conta conta);
}
