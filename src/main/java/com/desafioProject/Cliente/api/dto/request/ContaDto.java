package com.desafioProject.Cliente.api.dto.request;

import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContaDto {

    private Long id;

    private Cliente cliente;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String numeroDaConta;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    @NotNull(message = "Campo não pode ser nulo!")
    @NotBlank(message = "Campo não pode ser vazio")
    private String digitoVerificador;

    @PositiveOrZero(message = "Campo não pode ser negativo!")
    private BigDecimal saldo;

    @PositiveOrZero(message = "Campo não pode ser negativo!")
    private BigDecimal taxa;

    private int saqueSemTaxa;

    private String dataCriacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
}
