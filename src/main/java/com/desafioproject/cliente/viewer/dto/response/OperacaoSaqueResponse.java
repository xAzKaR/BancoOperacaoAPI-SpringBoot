package com.desafioproject.cliente.viewer.dto.response;

import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class OperacaoSaqueResponse {

    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    private String numeroConta;

    private BigDecimal valorTransacao;

    private String mensagem;

}