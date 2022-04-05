package com.live.kafka.consumer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
