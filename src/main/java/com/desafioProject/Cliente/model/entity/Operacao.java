package com.desafioProject.Cliente.model.entity;

import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Tipo de operação não pode ser vazio!")
    @NotNull(message = "Tipo de operação não pode ser nulo!")
    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    @NotNull(message = "Campo número da conta não pode ser nulo!")
    @NotEmpty(message = "Campo número da conta não pode ser vazio!")
    private String numeroConta;

    private String contaDestino;

    @NotNull(message = "Campo transação não pode ser nulo!")
    @NotEmpty(message = "Campo transação não pode ser vazio!")
    private BigDecimal valorTransacao;

    private BigDecimal taxaDeTransferencia;

    private BigDecimal saldo;

}
