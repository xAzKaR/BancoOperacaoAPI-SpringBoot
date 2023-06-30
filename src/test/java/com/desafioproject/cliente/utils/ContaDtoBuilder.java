package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import com.desafioproject.cliente.viewer.dto.request.ContaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDtoBuilder {

    private ContaDto contaDto;

    public static ContaDtoBuilder contaFisicaDto() {
        ContaDtoBuilder builder = new ContaDtoBuilder();
        builder.contaDto = new ContaDto();
        builder.contaDto.setId(1L);
        builder.contaDto.setCliente(ClienteBuilder.clienteCpf().criar());
        builder.contaDto.setNumeroDaConta("111");
        builder.contaDto.setAgencia("7");
        builder.contaDto.setTipo(TipoDeConta.Fisica);
        builder.contaDto.setDigitoVerificador("7");
        builder.contaDto.setSaldo(BigDecimal.ZERO);
        builder.contaDto.setSaqueSemTaxa(TipoDeConta.Fisica.getQuantidadeDeSaque());
        builder.contaDto.setTaxa(TipoDeConta.Fisica.getTaxa());
        return builder;
    }


    public static ContaDtoBuilder contaJuridicaDto() {
        ContaDtoBuilder builder = new ContaDtoBuilder();
        builder.contaDto = new ContaDto();
        builder.contaDto.setId(2L);
        builder.contaDto.setCliente(ClienteBuilder.clienteCnpj().criar());
        builder.contaDto.setNumeroDaConta("222");
        builder.contaDto.setAgencia("7");
        builder.contaDto.setTipo(TipoDeConta.Juridica);
        builder.contaDto.setDigitoVerificador("7");
        return builder;
    }

    public static ContaDtoBuilder contaGovernamentalDto() {
        ContaDtoBuilder builder = new ContaDtoBuilder();
        builder.contaDto = new ContaDto();
        builder.contaDto.setId(3L);
        builder.contaDto.setCliente(ClienteBuilder.clienteCompleto().criar());
        builder.contaDto.setNumeroDaConta("333");
        builder.contaDto.setAgencia("7");
        builder.contaDto.setTipo(TipoDeConta.Governamental);
        builder.contaDto.setDigitoVerificador("7");
        return builder;
    }

    public static ContaDtoBuilder contaSemDadosDto() {
        ContaDtoBuilder builder = new ContaDtoBuilder();
        builder.contaDto = new ContaDto();
        builder.contaDto.setCliente(ClienteBuilder.clienteCompleto().criar());
        builder.contaDto.setAgencia("7");
        builder.contaDto.setTipo(TipoDeConta.Governamental);
        builder.contaDto.setDigitoVerificador("7");
        return builder;
    }

    public ContaDto criar() {
        return contaDto;
    }
}
