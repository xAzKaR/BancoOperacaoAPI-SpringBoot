package com.live.kafka.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    private Long id;

    private String numeroDaConta;

    private String agencia;

    private String tipo;

    private String digitoVerificador;

    private BigDecimal saldo;

    private BigDecimal taxa;

    private int saqueSemTaxa;

    private String dataCriacao;

}
