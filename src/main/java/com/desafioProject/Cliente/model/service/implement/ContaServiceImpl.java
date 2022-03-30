package com.desafioProject.Cliente.model.service.implement;

import com.desafioProject.Cliente.api.dto.request.ContaDto;
import com.desafioProject.Cliente.api.dto.response.ContaResponse;
import com.desafioProject.Cliente.api.exception.ContaExistsException;
import com.desafioProject.Cliente.api.exception.ContaNotFoundException;
import com.desafioProject.Cliente.api.mappers.MapperConta;
import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import com.desafioProject.Cliente.model.producer.ContaProducer;
import com.desafioProject.Cliente.model.repository.ContaRepository;
import com.desafioProject.Cliente.model.service.ContaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository repository;
    private final ModelMapper modelMapper;
    private final MapperConta mapperConta;
    private final ContaProducer contaProducer;

    @Override
    public ContaResponse salvar(ContaDto contaDto) {
        ifExisteNumeroDaContaRetornaException(contaDto);
        TipoDeConta tipoDeConta = contaDto.getTipo();
        Conta conta = mapperConta.toModel(contaDto);
        conta.setSaqueSemTaxa(tipoDeConta.getQuantidadeDeSaque());
        conta.setTaxa(tipoDeConta.getTaxa());
        conta.setSaldo(BigDecimal.ZERO);

        contaProducer.enviar(conta);
        repository.save(conta);
        ContaResponse contaResponseRetorno = mapperConta.toResponse(conta);
        return contaResponseRetorno;
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ContaDto localizarId(Long id) {
        EncontrarNumeroDaConta(repository.findById(id).get());
        Conta conta = repository.findById(id).get();
        return mapperConta.toDto(conta);
    }

    @Override
    public ContaDto locazalicarConta(String numeroDaConta) {
        EncontrarNumeroDaConta(repository.findBynumeroDaConta(numeroDaConta));
        Conta conta = repository.findBynumeroDaConta(numeroDaConta);
        return mapperConta.toDto(conta);
    }


    @Override
    public ContaDto atualizar(ContaDto contaDto, Long id) {
        Conta conta = repository.findById(id).orElseThrow(ContaNotFoundException::new);
        mapperConta.atualizar(contaDto, conta);
        repository.save(conta);
        ContaDto contaDtoRetorno = mapperConta.toDto(conta);
        return contaDtoRetorno;
    }

    @Override
    public ResponseEntity<ContaDto> atualizar2(ContaDto contaDto, Long id) {
        return repository.findById(id)
                .map(registro -> {
                    registro.setAgencia(contaDto.getAgencia());
                    registro.setDigitoVerificador(contaDto.getDigitoVerificador());
                    registro.setTipo(contaDto.getTipo());
                    ContaDto contaDtoRetorno = mapperConta.toDto(registro);
                    return ResponseEntity.ok().body(contaDtoRetorno);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<ContaDto> listar() {
        List<Conta> listaConta = repository.findAll();
        return listaConta
                .stream()
                .map(mapperConta::toDto)
                .collect(Collectors.toList());
    }

    private void ifExisteNumeroDaContaRetornaException(ContaDto contaDto) {
        if (repository.existsBynumeroDaConta(contaDto.getNumeroDaConta())) {
            throw new ContaExistsException();
        }
    }

    private void EncontrarNumeroDaConta(Conta repository) {
        if (repository == null) {
            throw new ContaNotFoundException();
        }
    }
}
