package com.desafioProject.Cliente.utils;

import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import com.desafioProject.Cliente.viewer.dto.request.OperacaoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacaoDtoBuilder {

    private OperacaoDto operacaoDto;

    public static OperacaoDtoBuilder operacaoDeposito() {
        OperacaoDtoBuilder builder = new OperacaoDtoBuilder();
        builder.operacaoDto = new OperacaoDto();
        builder.operacaoDto.setId(1L);
        builder.operacaoDto.setNumeroConta("222");
        builder.operacaoDto.setValorTransacao(new BigDecimal(100));
        builder.operacaoDto.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        return builder;
    }

    public static OperacaoDtoBuilder operacaoDtoDepositoValorNegativo() {
        OperacaoDtoBuilder builder = new OperacaoDtoBuilder();
        builder.operacaoDto = new OperacaoDto();
        builder.operacaoDto.setId(1L);
        builder.operacaoDto.setNumeroConta("222");
        builder.operacaoDto.setValorTransacao(new BigDecimal(-100));
        builder.operacaoDto.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        return builder;
    }

    public static OperacaoDtoBuilder operacaoDtoDepositoValorZerado() {
        OperacaoDtoBuilder builder = new OperacaoDtoBuilder();
        builder.operacaoDto = new OperacaoDto();
        builder.operacaoDto.setId(1L);
        builder.operacaoDto.setNumeroConta("222");
        builder.operacaoDto.setValorTransacao(new BigDecimal(0));
        builder.operacaoDto.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        return builder;
    }

    public static OperacaoDtoBuilder operacaoSaque() {
        OperacaoDtoBuilder builder = new OperacaoDtoBuilder();
        builder.operacaoDto = new OperacaoDto();
        builder.operacaoDto.setId(1L);
        builder.operacaoDto.setNumeroConta("222");
        builder.operacaoDto.setValorTransacao(new BigDecimal(100));
        return builder;
    }

    public static OperacaoDtoBuilder operacaoTransferencia() {
        OperacaoDtoBuilder builder = new OperacaoDtoBuilder();
        builder.operacaoDto = new OperacaoDto();
        builder.operacaoDto.setId(1L);
        builder.operacaoDto.setNumeroConta("222");
        builder.operacaoDto.setValorTransacao(new BigDecimal(100));
        builder.operacaoDto.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        return builder;
    }

    public OperacaoDto criar() {
        return operacaoDto;
    }


}
