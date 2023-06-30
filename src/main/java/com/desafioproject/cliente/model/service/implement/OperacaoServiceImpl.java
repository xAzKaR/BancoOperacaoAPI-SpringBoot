package com.desafioproject.cliente.model.service.implement;

import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import com.desafioproject.cliente.model.producerTaxas.OperacaoProducer;
import com.desafioproject.cliente.model.repository.ContaRepository;
import com.desafioproject.cliente.model.repository.OperacaoRepository;
import com.desafioproject.cliente.model.service.OperacaoService;
import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioproject.cliente.viewer.exception.*;
import com.desafioproject.cliente.viewer.mappers.MapperOperacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class OperacaoServiceImpl implements OperacaoService {

    private final OperacaoRepository repository;
    private final MapperOperacao mapperOperacao;
    private final ContaRepository contaRepository;
    private final OperacaoProducer operacaoProducer;
    private Jedis jedis = new Jedis();

    @Override
    @Transactional
    public OperacaoDepositoResponse depositar(OperacaoDto operacaoDto) throws OperacaoNaoCompletadaException {
        ifGetNumeroDaConta(operacaoDto.getNumeroConta());
        ifValorMenorIgualZero(operacaoDto);

        Operacao operacao = mapperOperacao.toModel(operacaoDto);
        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        operacao.setConta(conta);

        double valorSaldo = conta.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();
        double novoSaldo = valorSaldo + valorDepositado;

        conta.setSaldo(BigDecimal.valueOf(novoSaldo));
        operacao.setSaldo(BigDecimal.valueOf(novoSaldo));

        operacao.setTipoDeOperacao(OperacaoEnum.DEPOSITO);

        var kafkaDeposito = Operacao.builder()
                .id(Math.abs(UUID.randomUUID().getLeastSignificantBits()))
                .numeroConta(conta.getNumeroDaConta())
                .tipoDeOperacao(OperacaoEnum.DEPOSITO)
                .valorTransacao(operacaoDto.getValorTransacao())
                .saldo(BigDecimal.valueOf(novoSaldo))
                .build();

        try {
            operacaoProducer.enviar(kafkaDeposito);
        } catch (Exception e) {
            throw new OperacaoNaoCompletadaException();
        }

        contaRepository.save(conta);
        repository.save(operacao);
        return mapperOperacao.toResponse(operacao);
    }


    @Override
    public OperacaoSaqueResponse sacar(OperacaoDto operacaoDto) {
        ifGetNumeroDaConta(operacaoDto.getNumeroConta());
        ifValorMenorIgualZero(operacaoDto);

        Operacao operacao = mapperOperacao.toModel(operacaoDto);
        Conta conta = contaRepository.findBynumeroDaConta(operacaoDto.getNumeroConta());

        double valorDoSaque = operacaoDto.getValorTransacao().doubleValue();
        double valorDoSaldo = conta.getSaldo().doubleValue();

        ifChecaSaldoMenorValorTransacao(conta, operacaoDto);

        TipoDeConta tipoDeConta = conta.getTipo();
        operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);

        BigDecimal saque1 = BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque));
        BigDecimal saque2 = BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque).add(conta.getTaxa()));

        operacao.setConta(conta);

        OperacaoSaqueResponse operacaoSaqueResponse = mapperOperacao.toSaqueResponse(operacao);

        int quantidadeDesaque = Integer.parseInt(jedis.get(conta.getNumeroDaConta()));
        operacaoSaque(operacaoDto, operacao, conta, tipoDeConta, quantidadeDesaque, saque1, saque2, operacaoSaqueResponse);

        Operacao kafkaOperacao = criafKafkaOperacao(operacaoDto, conta, tipoDeConta);

        try {
            operacaoProducer.enviar(kafkaOperacao);
            contaRepository.save(conta);
            repository.save(operacao);
        } catch (Exception ex) {
            throw new OperacaoNaoCompletadaException();
        }

        return operacaoSaqueResponse;
    }

    @Override
    public OperacaoTransfResponse transferir(OperacaoDto operacaoDto) {
        ifContaExistenteRetornaNotFound(operacaoDto);
        ifValorMenorIgualZero(operacaoDto);

        Operacao operacao = mapperOperacao.toModel(operacaoDto);

        Conta contaTransferencia = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());
        Conta contaTransferida = contaRepository.findBynumeroDaConta(operacao.getContaDestino());

        double valorSaldoContaTransferencia = contaTransferencia.getSaldo().doubleValue();
        double valorSaldoContaTransferida = contaTransferida.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();

        ifChecaSaldoMenorValorTransacao(contaTransferencia, operacaoDto);

        contaTransferencia.setSaldo(BigDecimal.valueOf(valorSaldoContaTransferencia).subtract(BigDecimal.valueOf(valorDepositado)));
        contaTransferida.setSaldo(BigDecimal.valueOf(valorSaldoContaTransferida).add(BigDecimal.valueOf(valorDepositado)));

        operacao.setSaldo(BigDecimal.valueOf(contaTransferencia.getSaldo().doubleValue()));

        operacao.setConta(contaTransferencia);

        contaRepository.save(contaTransferencia);
        contaRepository.save(contaTransferida);

        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);

        repository.save(operacao);

        return mapperOperacao.toTransfResponse(operacao);
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

    private void ifValorMenorIgualZero(OperacaoDto operacaoDto) {
        if (operacaoDto.getValorTransacao().doubleValue() <= 0) {
            throw new ClienteNegativoOrZeroException();
        }
    }

    private void ifGetNumeroDaConta(String operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto)) {
            throw new ContaNotFoundException();
        }
    }

    private void ifChecaSaldoMenorValorTransacao(Conta conta, OperacaoDto operacaoDto) {
        if (conta.getSaldo().doubleValue() < operacaoDto.getValorTransacao().doubleValue()) {
            throw new ClienteSaldoException();
        }
    }

    private void ifContaExistenteRetornaNotFound(OperacaoDto operacaoDto) {
        if (!contaRepository.existsBynumeroDaConta(operacaoDto.getNumeroConta())) {
            throw new ContaNotFoundException();
        } else ifGetNumeroDaConta(operacaoDto.getContaDestino());
    }

    private void operacaoSaque(OperacaoDto operacaoDto, Operacao operacao, Conta conta, TipoDeConta tipoDeConta, int quantidadeDesaque, BigDecimal saque1, BigDecimal saque2, OperacaoSaqueResponse operacaoSaqueResponse) {
        if (quantidadeDesaque > 0) {
            quantidadeDesaque--;
            conta.setSaldo(saque1);

            conta.setSaqueSemTaxa(Integer.parseInt(jedis.get(conta.getNumeroDaConta())));
            operacao.setSaldo(BigDecimal.valueOf(conta.getSaldo().doubleValue()));

            jedis.set(conta.getNumeroDaConta(), Integer.toString(quantidadeDesaque));
            operacaoSaqueResponse.setMensagem("Saque efetuado com sucesso, você possui mais " + Integer.parseInt(jedis.get(conta.getNumeroDaConta())) + " saques disponíveis " +
                    "após, será cobrado R$: " + tipoDeConta.getTaxa() + ",00");
        } else if (conta.getSaldo().doubleValue() > (conta.getTaxa().doubleValue() + operacaoDto.getValorTransacao().doubleValue())) {
            conta.setSaldo(saque2);
            operacao.setSaldo(BigDecimal.valueOf(conta.getSaldo().doubleValue()));

            conta.setSaqueSemTaxa(Integer.parseInt(conta.getNumeroDaConta()));

            operacaoSaqueResponse.setMensagem("Saque com taxa efetuado com sucesso, será adicionado um valor  de R$: "
                    + tipoDeConta.getTaxa() + ",00 verifique com seu gerente um novo plano");
        }
    }

    private Operacao criafKafkaOperacao(OperacaoDto operacaoDto, Conta conta, TipoDeConta tipoDeConta) {
       return Operacao.builder()
                .id(Math.abs(UUID.randomUUID().getLeastSignificantBits()))
                .numeroConta(conta.getNumeroDaConta())
                .tipoDeOperacao(OperacaoEnum.SAQUE)
                .valorTransacao(operacaoDto.getValorTransacao())
                .taxaDeTransferencia(tipoDeConta.getTaxa())
                .saldo(conta.getSaldo())
                .build();
    }
}
