package com.desafioProject.Cliente.model.service.implement;

import com.desafioProject.Cliente.api.dto.request.OperacaoDto;
import com.desafioProject.Cliente.api.dto.response.OperacaoDepositoResponse;
import com.desafioProject.Cliente.api.dto.response.OperacaoSaqueResponse;
import com.desafioProject.Cliente.api.dto.response.OperacaoTransfResponse;
import com.desafioProject.Cliente.api.exception.ClienteNegativoOrZeroException;
import com.desafioProject.Cliente.api.exception.ClienteSaldoException;
import com.desafioProject.Cliente.api.exception.ContaNotFoundException;
import com.desafioProject.Cliente.api.exception.OperacaoNotFoundException;
import com.desafioProject.Cliente.api.mappers.MapperConta;
import com.desafioProject.Cliente.api.mappers.MapperOperacao;
import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.entity.enums.OperacaoEnum;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
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
    public OperacaoDepositoResponse depositar(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        }

        if (operacaoDto.getValorTransacao().doubleValue() <= 0) {
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
        repository.save(operacao);

        OperacaoDepositoResponse operacaoDepositoResponse = mapperOperacao.toResponse(operacao);

        return operacaoDepositoResponse;
    }

    @Override
    public OperacaoSaqueResponse sacar(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        }
        if (operacaoDto.getValorTransacao().doubleValue() <= 0) {
            throw new ClienteNegativoOrZeroException();
        }

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorDoSaque = operacaoDto.getValorTransacao().doubleValue();
        double valorDoSaldo = conta.getSaldo().doubleValue();
        operacao.setSaldo(BigDecimal.valueOf(valorDoSaldo));

        if (conta.getSaldo().doubleValue() < operacaoDto.getValorTransacao().doubleValue()) {
            throw new ClienteSaldoException();
        }

        conta.setSaldo(BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque)));
        operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);

        contaRepository.save(conta);

        repository.save(operacao);

        TipoDeConta tipoDeConta = conta.getTipo();
        tipoDeConta.getTaxa();

        OperacaoSaqueResponse operacaoSaqueResponse = mapperOperacao.toSaqueResponse(operacao);

        operacaoSaqueResponse.setMensagem("Saque efetuado com sucesso, você possui mais " + tipoDeConta.getQuantidadeDeSaque() + " saques disponíveis " +
                "após, será cobrado R$: " + tipoDeConta.getTaxa());

        return operacaoSaqueResponse;
    }

    @Override
    public OperacaoTransfResponse transferir(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        } else if (!contaRepository.existsBynumeroDaConta(operacaoDto.getContaDestino())) {
            throw new ContaNotFoundException();
        }
        if (operacaoDto.getValorTransacao().doubleValue() <= 0) {
            throw new ClienteNegativoOrZeroException();
        }

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta contaTransferencia = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());
        Conta contaTransferida = contaRepository.findBynumeroDaConta(operacao.getContaDestino());

        double valorSaldoContaTransferencia = contaTransferencia.getSaldo().doubleValue();
        double valorSaldocontaTransferida = contaTransferida.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();

        if (contaTransferencia.getSaldo().doubleValue() < operacaoDto.getValorTransacao().doubleValue()) {
            throw new ClienteSaldoException();
        }

        contaTransferencia.setSaldo(BigDecimal.valueOf(valorSaldoContaTransferencia).subtract(BigDecimal.valueOf(valorDepositado)));
        contaTransferida.setSaldo(BigDecimal.valueOf(valorSaldocontaTransferida).add(BigDecimal.valueOf(valorDepositado)));
        operacao.setSaldo(BigDecimal.valueOf(valorDepositado));

        contaRepository.save(contaTransferencia);
        contaRepository.save(contaTransferida);

        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);

        repository.save(operacao);

        OperacaoTransfResponse operacaoTransfRetorno = mapperOperacao.toTransfResponse(operacao);
        return operacaoTransfRetorno;
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
