package com.desafioProject.Cliente.utils;

import com.desafioProject.Cliente.viewer.dto.request.ClienteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDtoBuilder {

    ClienteDto clienteDto;

    public static ClienteDtoBuilder criarClienteDto() {
        ClienteDtoBuilder builder = new ClienteDtoBuilder();
        builder.clienteDto = new ClienteDto();
        builder.clienteDto.setId(1L);
        builder.clienteDto.setNome("José");
        builder.clienteDto.setCnpj("70.794.651/0001-86");
        builder.clienteDto.setCpf("149.945.810-02");
        builder.clienteDto.setTelefone("(11) 98521-1715");
        builder.clienteDto.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public static ClienteDtoBuilder criarClienteDtoSemDados() {
        ClienteDtoBuilder builder = new ClienteDtoBuilder();
        builder.clienteDto = new ClienteDto();
        builder.clienteDto.setId(1L);
        builder.clienteDto.setNome("José");
        builder.clienteDto.setTelefone("(11) 98521-1715");
        builder.clienteDto.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public ClienteDto criar() {
        return clienteDto;
    }

}
