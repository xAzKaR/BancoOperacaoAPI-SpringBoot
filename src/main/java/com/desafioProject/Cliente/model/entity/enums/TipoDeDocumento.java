package com.desafioProject.Cliente.model.entity.enums;

import com.desafioProject.Cliente.model.entity.enums.grupos.CnpjGroup;
import com.desafioProject.Cliente.model.entity.enums.grupos.CpfGroup;
import lombok.Getter;

@Getter
public enum TipoDeDocumento {
    FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class),
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);
//    GOVERNAMENTAL("Governamental", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

    private final String descricao;
    private final String documento;
    private final String mascara;
    private final Class<?> group;

    TipoDeDocumento(String descricao, String documento, String mascara, Class<?> group) {
        this.descricao = descricao;
        this.documento = documento;
        this.mascara = mascara;
        this.group = group;
    }

}
