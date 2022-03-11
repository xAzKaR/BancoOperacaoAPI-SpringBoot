package com.desafioProject.Cliente.model.entity.enums.grupos;

import com.desafioProject.Cliente.model.entity.Cliente;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

    @Override
    public List<Class<?>> getValidationGroups(Cliente cliente) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(Cliente.class);

        if(isPessoaSelecionada(cliente)){
            groups.add(cliente.getTipoDeDocumento().getGroup());
        }

        return groups;
    }

    private boolean isPessoaSelecionada(Cliente cliente) {
        return cliente != null && cliente.getTipoDeDocumento() != null;
    }
}
