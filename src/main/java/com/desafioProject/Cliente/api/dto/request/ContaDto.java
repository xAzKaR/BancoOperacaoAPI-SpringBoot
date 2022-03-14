package com.desafioProject.Cliente.api.dto.request;

import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDto {

    private Long id;

    private Cliente cliente;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String numeroDaConta;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String agencia;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String digitoVerificador;

    @PositiveOrZero(message = "Campo não pode ser negativo!")
    private BigDecimal saldo;

    @PositiveOrZero(message = "Campo não pode ser negativo!")
    private BigDecimal taxa;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
}
