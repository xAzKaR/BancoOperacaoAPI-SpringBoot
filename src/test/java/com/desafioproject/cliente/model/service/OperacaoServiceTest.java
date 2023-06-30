package com.desafioproject.cliente.model.service;

import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.producerTaxas.OperacaoProducer;
import com.desafioproject.cliente.model.repository.ContaRepository;
import com.desafioproject.cliente.model.repository.OperacaoRepository;
import com.desafioproject.cliente.model.service.implement.OperacaoServiceImpl;
import com.desafioproject.cliente.utils.*;
import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.exception.ContaExistsException;
import com.desafioproject.cliente.viewer.exception.ContaNotFoundException;
import com.desafioproject.cliente.viewer.mappers.MapperOperacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OperacaoServiceImpl.class)
public class OperacaoServiceTest {

    @Autowired
    OperacaoServiceImpl operacaoService;

    @MockBean
    MapperOperacao mapperOperacao;

    @MockBean
    ContaRepository contaRepository;

    @MockBean
    OperacaoRepository repository;

    @MockBean
    OperacaoProducer operacaoProducer;

    @Test
    @DisplayName("Se não existir a conta, retornar exception")
    void seNaoExistirAContaRetornarExceptionTest() {
        OperacaoDto operacaoDto = OperacaoDtoBuilder.operacaoTransferencia().criar();
        Operacao operacao = OperacaoBuilder.operacaoTransferencia().criar();

        when(contaRepository.existsBynumeroDaConta(operacao.getNumeroConta())).thenThrow(ContaExistsException.class);
        Throwable exception = Assertions.catchThrowable(() -> operacaoService.depositar(operacaoDto));

        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro se a conta de operação não existir")
    void deveLancarErroSeAContaDaOperacaoNaoExistirTest() {
        OperacaoDto operacaoDto = OperacaoDtoBuilder.operacaoTransferencia().criar();
        Operacao operacao = OperacaoBuilder.operacaoTransferencia().criar();
//        OperacaoDepositoResponse response = OperacaoDepositoResponseBuilder.operacaoResponseDeposito().criar();

        repository.save(operacao);
        when(operacaoDto.getNumeroConta()).thenThrow(ContaNotFoundException.class);
        assertThrows(ContaNotFoundException.class, () -> operacaoService.depositar(operacaoDto));

    }

    @Test
    @DisplayName("Deve remover uma operação")
    void deveRemoverUmaOperacaoTest() {
        Operacao operacao = OperacaoBuilder.operacaoTransferencia().criar();

        repository.save(operacao);
        assertDoesNotThrow(() -> operacaoService.deletar(operacao.getId()));

        Mockito.verify(repository, Mockito.times(1)).deleteById(operacao.getId());
    }

    @Test
    @DisplayName("Deve retornar exception caso o valor de deposito seja igual a zero")
    void deveRetornarExceptionComValorDepositadoIgualAZeroTest() {
        OperacaoDto operacaoDto = OperacaoDtoBuilder.operacaoTransferencia().criar();
        Operacao operacao = OperacaoBuilder.operacaoDepositoValorZerado().criar();
        Conta conta = ContaBuilder.contaJuridica().criar();

        contaRepository.save(conta);
        when(mapperOperacao.toModel(operacaoDto)).thenReturn(operacao);
        when(contaRepository.findBynumeroDaConta(operacao.getNumeroConta())).thenReturn(conta);

        repository.save(operacao);
        when(operacaoDto.getValorTransacao()).thenThrow(ContaNotFoundException.class);
        assertThrows(ContaNotFoundException.class, () -> operacaoService.depositar(operacaoDto));

    }
}
