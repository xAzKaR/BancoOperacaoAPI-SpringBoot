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
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDto {

    private Long id;
    private Cliente cliente;
    private String numeroDaConta;
    private String agencia;
    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;
    private String digitoVerificador;
    private BigDecimal saldo;
    private BigDecimal taxa;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
}
