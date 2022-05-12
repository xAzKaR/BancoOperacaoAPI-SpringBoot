package com.desafioProject.Cliente.viewer.dto.request;

import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperacaoDto {

    private Long id;

    @Enumerated(EnumType.STRING)
    private OperacaoEnum tipoDeOperacao;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String numeroConta;

    private String contaDestino;

    private BigDecimal valorTransacao;

    private BigDecimal taxaDeTransferencia;

    private BigDecimal saldo;

}
