package com.desafioProject.Cliente.api.mappers;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;
import com.desafioProject.Cliente.model.entity.Operacao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperOperacao {
    OperacaoDto toDto(Operacao operacao);

    void atualizar(OperacaoDto operacaoDto, @MappingTarget Operacao operacao);

    Operacao toModel(OperacaoDto operacaoDto);
}
