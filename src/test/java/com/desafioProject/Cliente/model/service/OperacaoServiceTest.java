package com.desafioProject.Cliente.model.service;

import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.Operacao;
import com.desafioProject.Cliente.model.producerTaxas.OperacaoProducer;
import com.desafioProject.Cliente.model.repository.ContaRepository;
import com.desafioProject.Cliente.model.repository.OperacaoRepository;
import com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl;
import com.desafioProject.Cliente.utils.*;
import com.desafioProject.Cliente.viewer.dto.request.OperacaoDto;
import com.desafioProject.Cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioProject.Cliente.viewer.exception.ClienteNegativoOrZeroException;
import com.desafioProject.Cliente.viewer.exception.ContaExistsException;
import com.desafioProject.Cliente.viewer.exception.ContaNotFoundException;
import com.desafioProject.Cliente.viewer.exception.OperacaoNaoCompletadaException;
import com.desafioProject.Cliente.viewer.mappers.MapperOperacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

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
