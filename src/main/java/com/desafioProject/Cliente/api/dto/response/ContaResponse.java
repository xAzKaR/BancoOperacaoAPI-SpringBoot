package com.desafioProject.Cliente.api.dto.response;

import com.desafioProject.Cliente.api.dto.request.ClienteDto;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
public class ContaResponse {

    private String numeroDaConta;

    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipo;

    private String digitoVerificador;

    private String mensagem = "Conta criada com sucesso!";

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
}
