package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import com.desafioproject.cliente.viewer.dto.response.ContaResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaResponseBuilder {

    ContaResponse contaResponse;

    public static ContaResponseBuilder responseContaFisica() {
        ContaResponseBuilder builder = new ContaResponseBuilder();
        builder.contaResponse = new ContaResponse();
        builder.contaResponse.setNumeroDaConta("111");
        builder.contaResponse.setAgencia("7");
        builder.contaResponse.setTipo(TipoDeConta.Fisica);
        builder.contaResponse.setDigitoVerificador("7");
        builder.contaResponse.setMensagem("Conta criada com sucesso!");
        builder.contaResponse.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        return builder;
    }

    public static ContaResponseBuilder responseContaJuridica() {
        ContaResponseBuilder builder = new ContaResponseBuilder();
        builder.contaResponse = new ContaResponse();
        builder.contaResponse.setNumeroDaConta("222");
        builder.contaResponse.setAgencia("7");
        builder.contaResponse.setTipo(TipoDeConta.Juridica);
        builder.contaResponse.setDigitoVerificador("7");
        builder.contaResponse.setMensagem("Conta criada com sucesso!");
        builder.contaResponse.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        return builder;
    }

    public static ContaResponseBuilder responseContaGovernamental() {
        ContaResponseBuilder builder = new ContaResponseBuilder();
        builder.contaResponse = new ContaResponse();
        builder.contaResponse.setNumeroDaConta("333");
        builder.contaResponse.setAgencia("7");
        builder.contaResponse.setTipo(TipoDeConta.Governamental);
        builder.contaResponse.setDigitoVerificador("7");
        builder.contaResponse.setMensagem("Conta criada com sucesso!");
        builder.contaResponse.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        return builder;
    }

    public ContaResponse criar() {
        return contaResponse;
    }
}
