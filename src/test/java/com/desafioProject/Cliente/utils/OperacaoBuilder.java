package com.desafioProject.Cliente.utils;

import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
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
