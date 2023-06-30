package com.desafioproject.cliente.model.service;

import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import com.desafioproject.cliente.model.producerTaxas.ContaProducer;
import com.desafioproject.cliente.model.repository.ContaRepository;
import com.desafioproject.cliente.model.service.implement.ContaServiceImpl;
import com.desafioproject.cliente.utils.ContaBuilder;
import com.desafioproject.cliente.utils.ContaDtoBuilder;
import com.desafioproject.cliente.utils.ContaResponseBuilder;
import com.desafioproject.cliente.viewer.dto.request.ContaDto;
import com.desafioproject.cliente.viewer.dto.response.ContaResponse;
import com.desafioproject.cliente.viewer.exception.ContaExistsException;
import com.desafioproject.cliente.viewer.exception.ContaNotFoundException;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;
import com.desafioproject.cliente.viewer.mappers.MapperConta;
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
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ContaServiceImpl.class)
class ContaServiceTest {

    @Autowired
    ContaServiceImpl contaService;

    @MockBean
    MapperConta mapperConta;

    @MockBean
    ContaProducer contaProducer;

    @MockBean
    ContaRepository contaRepository;

    @Test
    @DisplayName("Deve salvar uma conta")
    void contaSalvaTest() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();
        ContaResponse contaResponse = ContaResponseBuilder.responseContaFisica().criar();

        when(contaRepository.existsBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(false);

        try {
            contaProducer.enviar(conta);
            contaRepository.save(conta);
        } catch (Exception e) {
            throw new OperacaoNaoCompletadaException();
        }

        when(mapperConta.toModel(contaDto)).thenReturn(conta);
        when(contaService.salvar(contaDto)).thenReturn(contaResponse);
        when(contaRepository.save(conta)).thenReturn(conta);

        assertThat(contaResponse.getNumeroDaConta()).isNotNull();
        assertThat(contaResponse.getTipo()).isEqualTo(TipoDeConta.Fisica);
        assertThat(contaResponse.getMensagem()).isEqualTo("Conta criada com sucesso!");
    }

    @Test
    @DisplayName("Deve obter uma conta pelo número da conta")
    void contaByNumeroDaContaTest() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();

        contaRepository.save(conta);
        when(contaRepository.findBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(conta);
        when(contaService.locazalicarConta(contaDto.getNumeroDaConta())).thenReturn(contaDto);

        assertThat(conta.getTipo()).isEqualTo(TipoDeConta.Fisica);
        assertThat(conta.getNumeroDaConta()).isEqualTo("111");
        assertThat(conta.getTaxa()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(conta.getSaqueSemTaxa()).isEqualTo(5);
    }

    @Test
    @DisplayName("Deve lançar erro se a conta existir")
    void seExistirNumeroDaContaRetornarException() {
        Conta conta = ContaBuilder.contaFisica().criar();
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();

        when(contaRepository.existsBynumeroDaConta(conta.getNumeroDaConta())).thenThrow(ContaExistsException.class);
        Throwable exception = Assertions.catchThrowable(() -> contaService.salvar(contaDto));

        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro se a conta existir")
    void seExistirNumeroDaContaRetornarException2() {
        Conta conta = ContaBuilder.contaFisica().criar();
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();

        contaRepository.save(conta);
        when(contaRepository.existsBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(false);
        when(contaRepository.existsBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(true).thenThrow(ContaExistsException.class);
        assertThrows(ContaExistsException.class, () -> contaService.salvar(contaDto));
    }


    @Test
    @DisplayName("Deve deletar uma conta física")
    void deletarContaFisicaTest() {
        Conta conta = ContaBuilder.contaFisica().criar();

        contaRepository.save(conta);
        assertDoesNotThrow(() -> contaService.deletar(conta.getId()));

        Mockito.verify(contaRepository, Mockito.times(1)).deleteById(conta.getId());
    }

    @Test
    @DisplayName("Deve deletar uma conta Jurídica")
    void deletarContaJuridicaTest() {
        Conta conta = ContaBuilder.contaJuridica().criar();

        contaRepository.save(conta);
        assertDoesNotThrow(() -> contaService.deletar(conta.getId()));

        Mockito.verify(contaRepository, Mockito.times(1)).deleteById(conta.getId());
    }


    @Test
    @DisplayName("Deve deletar uma conta Governamental")
    void deletarContaGovernamentalTest() {
        Conta conta = ContaBuilder.contaGovernamental().criar();

        contaRepository.save(conta);
        assertDoesNotThrow(() -> contaService.deletar(conta.getId()));

        Mockito.verify(contaRepository, Mockito.times(1)).deleteById(conta.getId());
    }

    @Test
    @DisplayName("Deve ocorrer exception ao tentar deletar uma conta não existente")
    void deletarContaInvalidaTest() {
        try {
            contaService.deletar(1L);
        } catch (Exception e) {
            throw new ContaNotFoundException();
        }
    }

    @Test
    @DisplayName("Deve lançar exception se a conta não for preenchida")
    void seNumeroDaContaForNulloRetornarExceptionTest() {
        ContaDto contaDto = ContaDtoBuilder.contaSemDadosDto().criar();

        when(contaRepository.existsBynumeroDaConta(Mockito.eq(null))).thenThrow(ContaNotFoundException.class);
        assertThrows(ContaNotFoundException.class, () -> contaService.locazalicarConta(contaDto.getNumeroDaConta()));
    }

    @Disabled
    @Test
    @DisplayName("Deve lançar exception se não for enviada a solicitação do kafka")
    void verificacaoDeRequisicaoDoKafkaExceptionTest() throws ExecutionException, InterruptedException, TimeoutException {
        Conta conta = ContaBuilder.contaFisica().criar();

        contaProducer.enviar(conta);
        assertThrows(OperacaoNaoCompletadaException.class, () -> contaProducer.enviar(conta)).wait(2);

    }

    @Test
    @DisplayName("Deve localizar uma conta pelo ID")
    void localizarContaIdTest() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();

        when(contaRepository.findById(contaDto.getId())).thenReturn(Optional.of(conta));
        when(mapperConta.toModel(contaDto)).thenReturn(conta);
        when(contaService.localizarId(contaDto.getId())).thenReturn(contaDto);

        assertThat(contaDto).isNotNull();
    }
}
