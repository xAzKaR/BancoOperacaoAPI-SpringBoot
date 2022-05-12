package com.desafioProject.Cliente.utils;

import com.desafioProject.Cliente.viewer.dto.response.ClienteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseBuilder {

    ClienteResponse clienteResponse;

    public static ClienteResponseBuilder criarResponse() {
        ClienteResponseBuilder builder = new ClienteResponseBuilder();
        builder.clienteResponse = new ClienteResponse();
        builder.clienteResponse.setNome("José");
        builder.clienteResponse.setMensagem("Cliente criado com sucesso!");
        return builder;
    }

    public ClienteResponse criar() {
        return clienteResponse;
    }
}
