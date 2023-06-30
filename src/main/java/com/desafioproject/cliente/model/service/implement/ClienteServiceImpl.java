package com.desafioproject.cliente.model.service.implement;

import com.desafioproject.cliente.model.entity.Cliente;
import com.desafioproject.cliente.model.repository.ClienteRepository;
import com.desafioproject.cliente.model.service.ClienteService;
import com.desafioproject.cliente.viewer.dto.request.ClienteDto;
import com.desafioproject.cliente.viewer.dto.response.ClienteResponse;
import com.desafioproject.cliente.viewer.exception.ClienteDocumentoNotBeNullException;
import com.desafioproject.cliente.viewer.exception.ClienteExistsException;
import com.desafioproject.cliente.viewer.exception.ClienteNotFoundException;
import com.desafioproject.cliente.viewer.mappers.MapperCliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;
    private final MapperCliente mapperCliente;

    @Override
    public ClienteResponse salvar(ClienteDto clienteDto) {
        ifExisteByCpfOuCnpj(clienteDto);
        ifCnpjOuCpfNull(clienteDto);
        return mapperCliente.toResponse(repository.save(mapperCliente.toModel(clienteDto)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ClienteDto findById(Long id) {
        return mapperCliente.toDto(repository
                .findById(id)
                .orElseThrow(ClienteNotFoundException::new));
    }

    @Override
    public ClienteDto localizarCpf(String cpf) {
        ifCpfNulleCpfInexistente(cpf);
        return mapperCliente.toDto(repository.findBycpf(cpf));
    }

    @Override
    public ClienteDto localizarCnpj(String cnpj) {
        ifCnpjNulleCnpjInexistente(cnpj);
        return mapperCliente.toDto(repository.findBycnpj(cnpj));
    }

    @Override
    public ClienteDto atualizar(ClienteDto clienteDto, Long id) {
        return repository
                .findById(id)
                .map(registro -> {
                    mapperCliente.atualizar(clienteDto, registro);
                    repository.save(registro);
                    return mapperCliente.toDto(registro);
                })
                .orElseThrow(ClienteNotFoundException::new);
    }

    @Override
    public ClienteDto atualizar2(ClienteDto clienteDto, Long id) {
        return repository
                .findById(id)
                .map(registro -> {
                    registro.setNome(clienteDto.getNome());
                    registro.setCnpj(clienteDto.getCnpj());
                    registro.setCpf(clienteDto.getCpf());
                    repository.save(registro);
                    return mapperCliente.toDto(registro);
                }).orElseThrow(ClienteNotFoundException::new);
    }

    @Override
    public List<ClienteDto> listar() {
        List<Cliente> listaCliente = (List<Cliente>) repository.findAll();
        return listaCliente
                .stream()
                .map(mapperCliente::toDto)
                .collect(Collectors.toList());
    }

    private void ifCpfNulleCpfInexistente(String cpf) {
        if (repository.findBycpf(cpf) == null) {
            throw new ClienteNotFoundException();
        }
    }

    private void ifCnpjNulleCnpjInexistente(String cnpj) {
        if (repository.findBycnpj(cnpj) == null) {
            throw new ClienteNotFoundException();
        }
    }

    private void ifExisteByCpfOuCnpj(ClienteDto clienteDto) {
        if (repository.existsBycpf(clienteDto.getCpf()) && repository.existsBycnpj(clienteDto.getCnpj())) {
            throw new ClienteExistsException();
        }
    }

    private void ifCnpjOuCpfNull(ClienteDto clienteDto) {
        if (clienteDto.getCnpj() == null && clienteDto.getCpf() == null) {
            throw new ClienteDocumentoNotBeNullException();
        }
    }
}
