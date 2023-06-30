package com.desafioproject.cliente.viewer.mappers;

import com.desafioproject.cliente.model.entity.Cliente;
import com.desafioproject.cliente.viewer.dto.request.ClienteDto;
import com.desafioproject.cliente.viewer.dto.response.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapperCliente {
    ClienteDto toDto(Cliente cliente);

    void atualizar(ClienteDto clienteDto, @MappingTarget Cliente cliente);

    Cliente toModel(ClienteDto clienteDto);

    ClienteResponse toResponse(Cliente cliente);
}
