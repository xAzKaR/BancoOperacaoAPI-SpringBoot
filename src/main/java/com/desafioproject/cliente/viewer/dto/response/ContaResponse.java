package com.desafioproject.cliente.viewer.dto.response;

import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ContaResponse {

    private String numeroDaConta;

    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    private String digitoVerificador;

    private String mensagem = "Conta criada com sucesso!";

    private String dataCriacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
}
