package com.desafioProject.Cliente.model.service.implement;


import com.desafioProject.Cliente.api.dto.request.ClienteDto;
import com.desafioProject.Cliente.api.exception.ClienteExistsException;
import com.desafioProject.Cliente.api.exception.ClienteNotFoundException;
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
    public ClienteDto salvar(ClienteDto clienteDto){
        if(repository.existsBydocumento(clienteDto.getDocumento())){
            throw new ClienteExistsException();
        }
        Cliente cliente = mapperCliente.toModel(clienteDto);

        ClienteDto clienteDtoRetorno = mapperCliente.toDto(cliente);

        repository.save(cliente);
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
    public ClienteDto localizarDocumento(String documento) {
        if(repository.findBydocumento(documento) == null){
            throw new ClienteNotFoundException();
        }

        Cliente clienteRetorno = repository.findBydocumento(documento);

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
                    registro.setDocumento(clienteDto.getDocumento());
                    registro.setTelefone(clienteDto.getTelefone());
                    registro.setEndereco(clienteDto.getEndereco());
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
}
