package com.desafioproject.cliente.model.entity.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum TipoDeConta{
    Fisica(new BigDecimal(10), 5),
    Juridica(new BigDecimal(10), 50),
    Governamental(new BigDecimal(20), 250);

    private final BigDecimal taxa;
    private final Integer quantidadeDeSaque;

    private static final Map<BigDecimal, TipoDeConta> taxaCalculada = new HashMap<>();
    private static final Map<Integer, TipoDeConta> quantidadeCalculada = new HashMap<>();

    static {
        for (TipoDeConta taxaCalculadaLista : TipoDeConta.values()) {
            taxaCalculada.put(taxaCalculadaLista.getTaxa(), taxaCalculadaLista);
        }
    }

    static {
        for (TipoDeConta quantidadeCalculadaLista : TipoDeConta.values()) {
            quantidadeCalculada.put(quantidadeCalculadaLista.quantidadeDeSaque, quantidadeCalculadaLista);
        }
    }

    TipoDeConta(BigDecimal taxa, Integer quantidadeDeSaque) {
        this.taxa = taxa;
        this.quantidadeDeSaque = quantidadeDeSaque;
    }

    public static TipoDeConta pegaTaxaPorValor(BigDecimal taxa) {
        return taxaCalculada.get(taxa);
    }

    public static TipoDeConta pegaQuantidadeCalculada(Integer quantidadeDeSaque) {
        return quantidadeCalculada.get(quantidadeDeSaque);
    }
}
