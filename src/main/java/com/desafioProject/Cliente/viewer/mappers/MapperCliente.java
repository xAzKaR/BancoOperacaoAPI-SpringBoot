package com.desafioProject.Cliente.viewer.mappers;

import com.desafioProject.Cliente.viewer.dto.request.ClienteDto;
import com.desafioProject.Cliente.viewer.dto.response.ClienteResponse;
import com.desafioProject.Cliente.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperCliente {
    ClienteDto toDto(Cliente cliente);

    @Mapping(target = "contas", source = "")
    void atualizar(ClienteDto clienteDto, @MappingTarget Cliente cliente);

    @Mapping(target = "contas", source = "")
    Cliente toModel(ClienteDto clienteDto);

    @Mapping(target = "mensagem", source = "")
    ClienteResponse toResponse(Cliente cliente);
}
