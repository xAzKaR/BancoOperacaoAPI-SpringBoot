package com.live.kafka.consumer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operacao {

    @Id
    private Long id;

    private String tipoDeOperacao;

    private String numeroConta;

    private String contaDestino;

    private BigDecimal valorTransacao;

    private BigDecimal taxaDeTransferencia;

    private BigDecimal saldo;
}
