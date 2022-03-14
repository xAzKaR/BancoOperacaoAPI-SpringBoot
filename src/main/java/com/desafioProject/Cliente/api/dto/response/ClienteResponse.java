package com.desafioProject.Cliente.api.dto.response;

import lombok.Data;

@Data
public class ClienteResponse {
    private String nome;
    private String mensagem = "Cliente criado com sucesso!";
}
