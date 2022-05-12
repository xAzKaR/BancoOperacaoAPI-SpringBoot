package com.desafioProject.Cliente.viewer.dto.response;
import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class OperacaoTransfResponse {

    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    private String numeroConta;

    private String contaDestino;

    private BigDecimal valorTransacao;

    private String mensagem = "Transferência efetuada com sucesso!";
}
