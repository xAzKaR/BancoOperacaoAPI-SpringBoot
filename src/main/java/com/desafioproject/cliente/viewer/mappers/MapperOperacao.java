package com.desafioproject.cliente.viewer.mappers;

import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoTransfResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperOperacao {
    OperacaoDto toDto(Operacao operacao);

    void atualizar(OperacaoDto operacaoDto, @MappingTarget Operacao operacao);

    Operacao toModel(OperacaoDto operacaoDto);

    OperacaoDepositoResponse toResponse(Operacao operacao);

    OperacaoSaqueResponse toSaqueResponse(Operacao operacao);

    OperacaoTransfResponse toTransfResponse(Operacao operacao);
}
