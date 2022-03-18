package com.desafioProject.Cliente.model.service.implement;

import com.desafioProject.Cliente.api.dto.request.ClienteDto;
import com.desafioProject.Cliente.api.dto.response.ClienteResponse;
import com.desafioProject.Cliente.api.exception.*;
import com.desafioProject.Cliente.api.mappers.MapperCliente;
import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.repository.ClienteRepository;
import com.desafioProject.Cliente.model.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;
    private final ModelMapper modelMapper;
    private final MapperCliente mapperCliente;

    @Override
    public ClienteResponse salvar(ClienteDto clienteDto){
        IfExistsCpfCnpjNotNull(clienteDto);
        Cliente cliente = mapperCliente.toModel(clienteDto);
        repository.save(cliente);
        ClienteResponse clienteDtoRetorno = mapperCliente.toResponse(cliente);
        return clienteDtoRetorno;
    }


    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ClienteDto findById(Long id) {
        Cliente cliente = repository
                .findById(id)
                .orElseThrow(ClienteNotFoundException::new);
        return mapperCliente.toDto(cliente);
    }

    @Override
    public ClienteDto localizarCpf(String cpf) {
        ifCpfNulleCpfInexistente(cpf);
        Cliente clienteRetorno = repository.findBycpf(cpf);
        return mapperCliente.toDto(clienteRetorno);
    }

    @Override
    public ClienteDto localizarCnpj(String cnpj) {
        ifCnpjNulleCnpjInexistente(cnpj);
        Cliente clienteRetorno = repository.findBycnpj(cnpj);
        return mapperCliente.toDto(clienteRetorno);
    }

    @Override
    public ClienteDto atualizar(ClienteDto clienteDto, Long id) {
        Cliente cliente = repository
                .findById(id)
                .orElseThrow(ClienteNotFoundException::new);
        mapperCliente.atualizar(clienteDto, cliente);
        repository.save(cliente);
        ClienteDto clienteDtoRetorno = mapperCliente.toDto(cliente);
        return clienteDtoRetorno;
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

    private void IfExistsCpfCnpjNotNull(ClienteDto clienteDto) {
        if(repository.existsBycpf(clienteDto.getCpf())){
            throw new ClienteExistsException();
        }
        if(repository.existsBycnpj(clienteDto.getCnpj())){
            throw new ClienteExistsByCnpjException();
        }
        if(clienteDto.getCnpj() == null && clienteDto.getCpf() == null){
            throw new ClienteDocumentoNotBeNullException();
        }
    }

    private void ifCpfNulleCpfInexistente(String cpf) {
        if(repository.findBycpf(cpf) == null || !repository.existsBycpf(cpf)){
            throw new ClienteNotFoundException();
        }
    }

    private void ifCnpjNulleCnpjInexistente(String cnpj) {
        if(repository.findBycnpj(cnpj) == null || !repository.existsBycnpj(cnpj)){
            throw new ClienteNotFoundException();
        }
    }
}
