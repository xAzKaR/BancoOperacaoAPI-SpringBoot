package com.desafioProject.Cliente.model.service.implement;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;
import com.desafioProject.Cliente.api.exception.ClienteNegativoOrZeroException;
import com.desafioProject.Cliente.api.exception.ClienteSaldoException;
import com.desafioProject.Cliente.api.exception.ContaNotFoundException;
import com.desafioProject.Cliente.api.exception.OperacaoNotFoundException;
import com.desafioProject.Cliente.api.mappers.MapperConta;
import com.desafioProject.Cliente.api.mappers.MapperOperacao;
import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import com.desafioProject.Cliente.model.repository.ContaRepository;
import com.desafioProject.Cliente.model.repository.OperacaoRepository;
import com.desafioProject.Cliente.model.service.OperacaoService;
import com.desafioProject.Cliente.model.service.ContaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class OperacaoServiceImpl implements OperacaoService {

    private final OperacaoRepository repository;
    private final ModelMapper modelMapper;
    private final MapperOperacao mapperOperacao;
    private final MapperConta mapperConta;
    private final ContaRepository contaRepository;
    private final ContaService contaService;


    @Override
    @Transactional
    public OperacaoDto depositar(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        }

        if(operacaoDto.getValorTransacao().doubleValue() <= 0){
            throw new ClienteNegativoOrZeroException();
        }

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorSaldo = conta.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();
        double novoSaldo = valorSaldo + valorDepositado;

        conta.setSaldo(BigDecimal.valueOf(novoSaldo));
        operacao.setSaldo(BigDecimal.valueOf(novoSaldo));
        contaRepository.save(conta);

        operacao.setTipoDeOperacao(OperacaoEnum.DEPOSITO);
        OperacaoDto operacaoDtoRetorno = mapperOperacao.toDto(operacao);

        repository.save(operacao);

        return operacaoDtoRetorno;
    }

    @Override
    public OperacaoDto sacar(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        }

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorDoSaque = operacaoDto.getValorTransacao().doubleValue();
        double valorDoSaldo = conta.getSaldo().doubleValue();

        if (conta.getSaldo().compareTo(BigDecimal.valueOf(valorDoSaldo)) <
                operacaoDto.getSaldo().compareTo(BigDecimal.valueOf(valorDoSaque))) {
            throw new ClienteSaldoException();
        }

        conta.setSaldo(BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque)));
        contaRepository.save(conta);

        operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);
        OperacaoDto operacaoDtoRetorno = mapperOperacao.toDto(operacao);

        return operacaoDtoRetorno;
    }

    @Override
    public OperacaoDto transferir(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        } else if (!contaRepository.existsBynumeroDaConta(operacaoDto.getContaDestino())) {
            throw new ContaNotFoundException();
        }

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorSaldo = conta.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();
        double novoSaldo = valorSaldo + valorDepositado;

        conta.setSaldo(BigDecimal.valueOf(novoSaldo));
        operacao.setSaldo(BigDecimal.valueOf(novoSaldo));

        contaRepository.save(conta);

        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);

        OperacaoDto operacaoDtoRetorno = mapperOperacao.toDto(operacao);

        repository.save(operacao);

        return operacaoDtoRetorno;
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OperacaoDto findById(Long id) {
        Operacao operacao = repository
                .findById(id)
                .orElseThrow(OperacaoNotFoundException::new);

        return mapperOperacao.toDto(operacao);
    }

    @Override
    public List<OperacaoDto> listar() {
        List<Operacao> listaOperacao = (List<Operacao>) repository.findAll();

        return listaOperacao
                .stream()
                .map(mapperOperacao::toDto)
                .collect(Collectors.toList());
    }
}
