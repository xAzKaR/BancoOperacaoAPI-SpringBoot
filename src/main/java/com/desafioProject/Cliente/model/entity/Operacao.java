package com.desafioProject.Cliente.model.entity;

import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

}
