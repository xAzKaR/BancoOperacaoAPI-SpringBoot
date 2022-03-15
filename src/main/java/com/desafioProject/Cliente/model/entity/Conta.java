package com.desafioProject.Cliente.model.entity;

import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String numeroDaConta;

    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    private String digitoVerificador;

    private BigDecimal saldo;

    private BigDecimal taxa;

    private int saqueSemTaxa;

    private String dataCriacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
}
