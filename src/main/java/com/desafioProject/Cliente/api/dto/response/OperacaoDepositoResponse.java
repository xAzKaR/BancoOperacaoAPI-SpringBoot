package com.desafioProject.Cliente.api.dto.response;

import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class OperacaoDepositoResponse {

    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    private String numeroConta;

    private BigDecimal valorTransacao;

    private String mensagem = "Depósito efetuado com sucesso!";
}
