package com.desafioproject.cliente.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String cnpj;

    private String telefone;

    private String endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Conta> contas;
}
