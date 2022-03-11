package com.desafioProject.Cliente.model.entity.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TipoDeConta {
    Fisica(new BigDecimal(10), 5),
    Juridica(new BigDecimal(10), 50),
    Governamental(new BigDecimal(20), 250);

    private final BigDecimal taxa;
    private final Integer quantidadeDeSaque;


    TipoDeConta(BigDecimal taxa, Integer quantidadeDeSaque) {
        this.taxa = taxa;
        this.quantidadeDeSaque = quantidadeDeSaque;
    }
}
