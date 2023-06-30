package com.desafioproject.cliente.model.entity;

import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    private String numeroConta;

    private String contaDestino;

    private BigDecimal valorTransacao;

    private BigDecimal taxaDeTransferencia;

    private BigDecimal saldo;

    @JoinColumn(name = "conta_id")
    @ManyToOne
    private Conta conta;

}
