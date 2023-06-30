package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaBuilder {

    private Conta conta;

    public static ContaBuilder contaFisica() {
        ContaBuilder builder = new ContaBuilder();
        builder.conta = new Conta();
        builder.conta.setId(1L);
        builder.conta.setCliente(ClienteBuilder.clienteCpf().criar());
        builder.conta.setNumeroDaConta("111");
        builder.conta.setAgencia("7");
        builder.conta.setTipo(TipoDeConta.Fisica);
        builder.conta.setDigitoVerificador("7");
        builder.conta.setSaldo(BigDecimal.ZERO);
        builder.conta.setSaqueSemTaxa(TipoDeConta.Fisica.getQuantidadeDeSaque());
        builder.conta.setTaxa(TipoDeConta.Fisica.getTaxa());
        return builder;
    }


    public static ContaBuilder contaJuridica() {
        ContaBuilder builder = new ContaBuilder();
        builder.conta = new Conta();
        builder.conta.setId(2L);
        builder.conta.setCliente(ClienteBuilder.clienteCnpj().criar());
        builder.conta.setNumeroDaConta("222");
        builder.conta.setAgencia("7");
        builder.conta.setTipo(TipoDeConta.Juridica);
        builder.conta.setDigitoVerificador("7");
        builder.conta.setSaqueSemTaxa(TipoDeConta.Fisica.getQuantidadeDeSaque());
        builder.conta.setTaxa(TipoDeConta.Fisica.getTaxa());

        return builder;
    }

    public static ContaBuilder contaGovernamental() {
        ContaBuilder builder = new ContaBuilder();
        builder.conta = new Conta();
        builder.conta.setId(3L);
        builder.conta.setCliente(ClienteBuilder.clienteCompleto().criar());
        builder.conta.setNumeroDaConta("333");
        builder.conta.setAgencia("7");
        builder.conta.setTipo(TipoDeConta.Governamental);
        builder.conta.setDigitoVerificador("7");
        builder.conta.setSaqueSemTaxa(250);
        return builder;
    }

    public static ContaBuilder contaSemDados() {
        ContaBuilder builder = new ContaBuilder();
        builder.conta = new Conta();
        builder.conta.setCliente(ClienteBuilder.clienteCompleto().criar());
        builder.conta.setAgencia("7");
        builder.conta.setTipo(TipoDeConta.Governamental);
        builder.conta.setDigitoVerificador("7");
        builder.conta.setSaqueSemTaxa(250);
        return builder;
    }

    public Conta criar() {
        return conta;
    }
}
