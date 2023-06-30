package com.desafioproject.cliente.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum OperacaoEnum {

    TRANSFERENCIA(1), SAQUE(2), DEPOSITO(3);

    private int codigoDeOperacao;
}
