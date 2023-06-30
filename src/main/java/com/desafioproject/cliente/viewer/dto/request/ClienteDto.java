package com.desafioproject.cliente.viewer.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private Long id;

    @NotBlank(message = "Campo nome não pode ser vazio")
    @NotNull
    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    @CPF
    private String cpf = null;

    @Column(unique = true)
    @CNPJ
    private String cnpj = null;

    @Column(nullable = false)
    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$")
    private String telefone;

    @NotBlank
    @Column(nullable = false)
    private String endereco;
}