package com.desafioProject.Cliente.api.dto.request;

import com.desafioProject.Cliente.model.entity.enums.TipoDeDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private Long id;
    private String nome;
    private TipoDeDocumento tipoDeDocumento;
    private String documento;
    private String telefone;
    private String endereco;

}