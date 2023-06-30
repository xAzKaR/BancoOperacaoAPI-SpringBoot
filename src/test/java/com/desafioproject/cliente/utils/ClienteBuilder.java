package com.desafioproject.cliente.utils;

import com.desafioproject.cliente.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteBuilder {

    private Cliente cliente;

    public static ClienteBuilder clienteCompleto() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        builder.cliente.setId(1L);
        builder.cliente.setNome("José");
        builder.cliente.setCnpj("70.794.651/0001-86");
        builder.cliente.setCpf("149.945.810-02");
        builder.cliente.setTelefone("(11) 98521-1715");
        builder.cliente.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public static ClienteBuilder clienteCpf() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        builder.cliente.setId(1L);
        builder.cliente.setNome("José");
        builder.cliente.setCpf("149.945.810-02");
        builder.cliente.setTelefone("(11) 98521-1715");
        builder.cliente.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public static ClienteBuilder clienteCnpj() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        builder.cliente.setId(1L);
        builder.cliente.setNome("José");
        builder.cliente.setCpf("149.945.810-02");
        builder.cliente.setTelefone("(11) 98521-1715");
        builder.cliente.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public static ClienteBuilder criarClienteSemDados() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        builder.cliente.setId(1L);
        builder.cliente.setNome("José");
        builder.cliente.setTelefone("(11) 98521-1715");
        builder.cliente.setEndereco("Em algum lugar de SP Manda Localização");
        return builder;
    }

    public Cliente criar() {
        return cliente;
    }

}