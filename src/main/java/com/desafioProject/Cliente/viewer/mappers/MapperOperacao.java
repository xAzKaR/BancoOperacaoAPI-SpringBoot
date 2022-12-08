package com.desafioProject.Cliente.viewer.mappers;

import com.desafioProject.Cliente.viewer.dto.request.OperacaoDto;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioProject.Cliente.model.entity.Operacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperOperacao {
    OperacaoDto toDto(Operacao operacao);

    @Mapping(target = "conta", source = "")
    void atualizar(OperacaoDto operacaoDto, @MappingTarget Operacao operacao);

    @Mapping(target = "conta", source = "")
    Operacao toModel(OperacaoDto operacaoDto);

    @Mapping(target = "mensagem", source = "")
    OperacaoDepositoResponse toResponse(Operacao operacao);

    @Mapping(target = "mensagem", source = "")
    OperacaoSaqueResponse toSaqueResponse(Operacao operacao);

    @Mapping(target = "mensagem", source = "")
    OperacaoTransfResponse toTransfResponse(Operacao operacao);
}
