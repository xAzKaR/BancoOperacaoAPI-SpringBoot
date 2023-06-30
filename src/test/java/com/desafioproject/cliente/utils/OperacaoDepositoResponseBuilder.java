package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacaoDepositoResponseBuilder {

    private OperacaoDepositoResponse response;

    public static OperacaoDepositoResponseBuilder operacaoResponseDeposito() {
        OperacaoDepositoResponseBuilder builder = new OperacaoDepositoResponseBuilder();
        builder.response = new OperacaoDepositoResponse();
        builder.response.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        builder.response.setNumeroConta("222");
        builder.response.setValorTransacao(new BigDecimal(100));
        builder.response.setMensagem("Depósito efetuado com sucesso!");
        return builder;
    }

    public OperacaoDepositoResponse criar() {
        return response;
    }
}
