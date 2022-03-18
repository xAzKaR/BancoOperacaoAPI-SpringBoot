package com.desafioProject.Cliente.api.mappers;

import com.desafioProject.Cliente.api.dto.request.ClienteDto;
import com.desafioProject.Cliente.api.dto.response.ClienteResponse;
import com.desafioProject.Cliente.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperCliente {
    ClienteDto toDto(Cliente cliente);

    void atualizar(ClienteDto clienteDto, @MappingTarget Cliente cliente);

    Cliente toModel(ClienteDto clienteDto);

    ClienteResponse toResponse(Cliente cliente);
}
