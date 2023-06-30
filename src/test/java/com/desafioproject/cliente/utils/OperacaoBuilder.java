package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacaoBuilder {

    private Operacao operacao;

    public static OperacaoBuilder operacaoDeposito() {
        OperacaoBuilder builder = new OperacaoBuilder();
        builder.operacao = new Operacao();
        builder.operacao.setId(1L);
        builder.operacao.setNumeroConta("222");
        builder.operacao.setValorTransacao(new BigDecimal(100));
        builder.operacao.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        return builder;
    }

    public static OperacaoBuilder operacaoDepositoValorZerado() {
        OperacaoBuilder builder = new OperacaoBuilder();
        builder.operacao = new Operacao();
        builder.operacao.setId(1L);
        builder.operacao.setNumeroConta("222");
        builder.operacao.setValorTransacao(new BigDecimal(0));
        builder.operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);
        return builder;
    }

    public static OperacaoBuilder operacaoDepositoValorNegativo() {
        OperacaoBuilder builder = new OperacaoBuilder();
        builder.operacao = new Operacao();
        builder.operacao.setId(1L);
        builder.operacao.setNumeroConta("222");
        builder.operacao.setValorTransacao(new BigDecimal(-100));
        builder.operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);
        return builder;
    }

    public static OperacaoBuilder operacaoSaque() {
        OperacaoBuilder builder = new OperacaoBuilder();
        builder.operacao = new Operacao();
        builder.operacao.setId(1L);
        builder.operacao.setNumeroConta("222");
        builder.operacao.setValorTransacao(new BigDecimal(100));
        builder.operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);
        return builder;
    }

    public static OperacaoBuilder operacaoTransferencia() {
        OperacaoBuilder builder = new OperacaoBuilder();
        builder.operacao = new Operacao();
        builder.operacao.setId(1L);
        builder.operacao.setNumeroConta("222");
        builder.operacao.setValorTransacao(new BigDecimal(100));
        builder.operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        return builder;
    }

    public Operacao criar() {
        return operacao;
    }
}
