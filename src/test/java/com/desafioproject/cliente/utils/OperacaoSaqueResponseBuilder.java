package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacaoSaqueResponseBuilder {

    private OperacaoSaqueResponse response;

    public static OperacaoSaqueResponseBuilder operacaoResponseSaque() {
        OperacaoSaqueResponseBuilder builder = new OperacaoSaqueResponseBuilder();
        builder.response = new OperacaoSaqueResponse();
        builder.response.setTipoDeOperacao(OperacaoEnum.SAQUE);
        builder.response.setNumeroConta("222");
        builder.response.setValorTransacao(new BigDecimal(100));
        builder.response.setMensagem("Saque efetuado com sucesso!");
        return builder;
    }

    public OperacaoSaqueResponse criar() {
        return response;
    }
}
