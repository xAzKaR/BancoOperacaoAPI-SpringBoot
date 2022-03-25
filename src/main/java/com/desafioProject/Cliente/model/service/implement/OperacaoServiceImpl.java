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
import com.desafioProject.Cliente.model.producer.OperacaoProducer;
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
import java.util.UUID;
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
    private final OperacaoProducer operacaoProducer;

    @Override
    @Transactional
    public OperacaoDepositoResponse depositar(OperacaoDto operacaoDto) {
        ifGetNumeroDaConta(operacaoDto.getNumeroConta());
        ifValorMenorIgualZero(operacaoDto);

        Operacao operacao = mapperOperacao.toModel(operacaoDto);
        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorSaldo = conta.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();
        double novoSaldo = valorSaldo + valorDepositado;

        conta.setSaldo(BigDecimal.valueOf(novoSaldo));
        operacao.setSaldo(BigDecimal.valueOf(novoSaldo));

        operacao.setTipoDeOperacao(OperacaoEnum.DEPOSITO);

        contaRepository.save(conta);
        repository.save(operacao);
        return mapperOperacao.toResponse(operacao);
    }


    @Override
    public OperacaoSaqueResponse sacar(OperacaoDto operacaoDto) {
        ifGetNumeroDaConta(operacaoDto.getNumeroConta());
        ifValorMenorIgualZero(operacaoDto);

        Operacao operacao = mapperOperacao.toModel(operacaoDto);
        Conta conta = contaRepository.findBynumeroDaConta(operacao.getNumeroConta());

        double valorDoSaque = operacaoDto.getValorTransacao().doubleValue();
        double valorDoSaldo = conta.getSaldo().doubleValue();

        ifChecaSaldoMenorValorTransacao(conta, operacaoDto);

        TipoDeConta tipoDeConta = conta.getTipo();
        operacao.setTipoDeOperacao(OperacaoEnum.SAQUE);

        long quantidadeDeSaque = conta.getSaqueSemTaxa();

        BigDecimal saque1 = BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque));
        BigDecimal saque2 = BigDecimal.valueOf(valorDoSaldo).subtract(BigDecimal.valueOf(valorDoSaque).add(conta.getTaxa()));

        OperacaoSaqueResponse operacaoSaqueResponse = mapperOperacao.toSaqueResponse(operacao);

        OperacaoSaque(operacaoDto, operacao, conta, tipoDeConta, quantidadeDeSaque, saque1, saque2, operacaoSaqueResponse);

//        KafkaTeste
        var KafkaOperacao = Operacao.builder()
                        .id(Math.abs(UUID.randomUUID().getLeastSignificantBits()))
                        .numeroConta(conta.getNumeroDaConta())
                        .tipoDeOperacao(OperacaoEnum.SAQUE)
                        .valorTransacao(operacaoDto.getValorTransacao())
                        .taxaDeTransferencia(tipoDeConta.getTaxa())
                        .saldo(conta.getSaldo())
                        .build();

        operacaoProducer.enviar(KafkaOperacao);
        contaRepository.save(conta);
        repository.save(operacao);
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
        double valorSaldocontaTransferida = contaTransferida.getSaldo().doubleValue();
        double valorDepositado = operacao.getValorTransacao().doubleValue();

        ifChecaSaldoMenorValorTransacao(contaTransferencia, operacaoDto);

        contaTransferencia.setSaldo(BigDecimal.valueOf(valorSaldoContaTransferencia).subtract(BigDecimal.valueOf(valorDepositado)));
        contaTransferida.setSaldo(BigDecimal.valueOf(valorSaldocontaTransferida).add(BigDecimal.valueOf(valorDepositado)));

        operacao.setSaldo(BigDecimal.valueOf(contaTransferencia.getSaldo().doubleValue()));

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

    private void OperacaoSaque(OperacaoDto operacaoDto, Operacao operacao, Conta conta, TipoDeConta tipoDeConta, long quantidadeDeSaque, BigDecimal saque1, BigDecimal saque2, OperacaoSaqueResponse operacaoSaqueResponse) {
        if (quantidadeDeSaque > 0) {
            quantidadeDeSaque--;

            conta.setSaldo(saque1);

            operacao.setSaldo(BigDecimal.valueOf(conta.getSaldo().doubleValue()));
            conta.setSaqueSemTaxa(Math.toIntExact(quantidadeDeSaque));

            operacaoSaqueResponse.setMensagem("Saque efetuado com sucesso, você possui mais " + conta.getSaqueSemTaxa() + " saques disponíveis " +
                    "após, será cobrado R$: " + tipoDeConta.getTaxa() + ",00");
        } else if (conta.getSaldo().doubleValue() > (conta.getTaxa().doubleValue() + operacaoDto.getValorTransacao().doubleValue())) {
            conta.setSaldo(saque2);
            operacao.setSaldo(BigDecimal.valueOf(conta.getSaldo().doubleValue()));

            operacaoSaqueResponse.setMensagem("Saque com taxa efetuado com sucesso, será adicionado um valor  de R$: "
                    + tipoDeConta.getTaxa() + ",00 verifique com seu gerente um novo plano");
        }
    }
}
