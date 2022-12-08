package com.desafioProject.Cliente.viewer.mappers;

import com.desafioProject.Cliente.viewer.dto.request.ContaDto;
import com.desafioProject.Cliente.viewer.dto.response.ContaResponse;
import com.desafioProject.Cliente.model.entity.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperConta {
    ContaDto toDto(Conta conta);

    @Mapping(target = "operacoes", source = "")
    Conta toModel(ContaDto contaDto);

    @Mapping(target = "operacoes", source = "")
    void atualizar(ContaDto contaDto, @MappingTarget Conta conta);

    @Mapping(target = "mensagem", source = "")
    ContaResponse toResponse(Conta conta);
}
