package com.desafioProject.Cliente.model.entity;

import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotBlank
    @NotNull
    private String numeroDaConta;

    @NotBlank
    @NotNull
    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    @NotBlank
    @NotNull
    private String digitoVerificador;

    @NotBlank
    @NotNull
    private BigDecimal saldo;

    @NotBlank
    @NotNull
    private BigDecimal taxa;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
}
