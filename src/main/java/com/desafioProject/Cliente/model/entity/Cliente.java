package com.desafioProject.Cliente.model.entity;

import com.desafioProject.Cliente.model.entity.enums.grupos.ClienteGroupSequenceProvider;
import com.desafioProject.Cliente.model.entity.enums.grupos.CnpjGroup;
import com.desafioProject.Cliente.model.entity.enums.grupos.CpfGroup;
import com.desafioProject.Cliente.model.entity.enums.TipoDeDocumento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull(message = "Tipo documento é obrigatório")
    @NotBlank
    @Enumerated(EnumType.STRING)
    private TipoDeDocumento tipoDeDocumento;

    @NotNull
    @NotBlank(message = "CPF ou CNPJ obrigatório!")
    @CNPJ(groups = CpfGroup.class)
    @CPF(groups = CnpjGroup.class)
    private String documento;

    @NotNull
    @NotBlank
    private String telefone;

    @NotNull
    @NotBlank
    private String endereco;

    @JsonIgnore
    @OneToMany
    private List<Conta> contas;
}
