package com.desafioProject.Cliente.api.mappers;

import com.desafioProject.Cliente.api.dto.request.ContaDto;
import com.desafioProject.Cliente.model.entity.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperConta {
    ContaDto toDto(Conta conta);

    Conta toModel(ContaDto contaDto);

    void atualizar(ContaDto contaDto, @MappingTarget Conta conta);
}
