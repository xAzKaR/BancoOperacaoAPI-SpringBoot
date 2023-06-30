package com.desafioproject.cliente.model.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.desafioproject.cliente.model.entity.Cliente;
import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.entity.enums.OperacaoEnum;
import com.desafioproject.cliente.model.entity.enums.TipoDeConta;
import com.desafioproject.cliente.model.producerTaxas.OperacaoProducer;
import com.desafioproject.cliente.model.repository.ContaRepository;
import com.desafioproject.cliente.model.repository.OperacaoRepository;
import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoDepositoResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
import com.desafioproject.cliente.viewer.dto.response.OperacaoTransfResponse;
import com.desafioproject.cliente.viewer.exception.OperacaoNaoCompletadaException;
import com.desafioproject.cliente.viewer.mappers.MapperOperacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OperacaoServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OperacaoServiceImplTest {
    @MockBean
    private ContaRepository contaRepository;

    @MockBean
    private MapperOperacao mapperOperacao;

    @MockBean
    private OperacaoProducer operacaoProducer;

    @MockBean
    private OperacaoRepository operacaoRepository;

    @Autowired
    private OperacaoServiceImpl operacaoServiceImpl;

    /**
     * Method under test: {@link OperacaoServiceImpl#depositar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDepositar() throws OperacaoNaoCompletadaException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.math.BigDecimal.doubleValue()" because the return value of "com.desafioProject.Cliente.viewer.dto.request.OperacaoDto.getValorTransacao()" is null
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.ifValorMenorIgualZero(OperacaoServiceImpl.java:184)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.depositar(OperacaoServiceImpl.java:47)
        //   In order to prevent depositar(OperacaoDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   depositar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        operacaoServiceImpl.depositar(new OperacaoDto());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#depositar(OperacaoDto)}
     */
    @Test
    void testDepositar2()
            throws OperacaoNaoCompletadaException, InterruptedException, ExecutionException, TimeoutException {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);

        Operacao operacao1 = new Operacao();
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoDepositoResponse operacaoDepositoResponse = new OperacaoDepositoResponse();
        operacaoDepositoResponse.setMensagem("Mensagem");
        operacaoDepositoResponse.setNumeroConta("Numero Conta");
        operacaoDepositoResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoDepositoResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toResponse((Operacao) any())).thenReturn(operacaoDepositoResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);

        Cliente cliente3 = new Cliente();
        cliente3.setCnpj("Cnpj");
        cliente3.setContas(new ArrayList<>());
        cliente3.setCpf("Cpf");
        cliente3.setEndereco("Endereco");
        cliente3.setId(123L);
        cliente3.setNome("Nome");
        cliente3.setTelefone("Telefone");

        Conta conta3 = new Conta();
        conta3.setAgencia("Agencia");
        conta3.setCliente(cliente3);
        conta3.setDataCriacao("Data Criacao");
        conta3.setId(123L);
        conta3.setNumeroDaConta("Numero Da Conta");
        conta3.setOperacoes(new ArrayList<>());
        conta3.setSaldo(BigDecimal.valueOf(42L));
        conta3.setSaqueSemTaxa(1);
        conta3.setTaxa(BigDecimal.valueOf(42L));
        conta3.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenReturn(conta3);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        doNothing().when(operacaoProducer).enviar((Operacao) any());
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        OperacaoDepositoResponse actualDepositarResult = operacaoServiceImpl.depositar(operacaoDto);
        assertSame(operacaoDepositoResponse, actualDepositarResult);
        assertEquals("42", actualDepositarResult.getValorTransacao().toString());
        verify(operacaoRepository).save((Operacao) any());
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toResponse((Operacao) any());
        verify(contaRepository).existsBynumeroDaConta((String) any());
        verify(contaRepository).findBynumeroDaConta((String) any());
        verify(contaRepository).save((Conta) any());
        verify(operacaoProducer).enviar((Operacao) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#depositar(OperacaoDto)}
     */
    @Test
    void testDepositar3()
            throws OperacaoNaoCompletadaException, InterruptedException, ExecutionException, TimeoutException {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);

        Operacao operacao1 = new Operacao();
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoDepositoResponse operacaoDepositoResponse = new OperacaoDepositoResponse();
        operacaoDepositoResponse.setMensagem("Mensagem");
        operacaoDepositoResponse.setNumeroConta("Numero Conta");
        operacaoDepositoResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoDepositoResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toResponse((Operacao) any())).thenReturn(operacaoDepositoResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);

        Cliente cliente3 = new Cliente();
        cliente3.setCnpj("Cnpj");
        cliente3.setContas(new ArrayList<>());
        cliente3.setCpf("Cpf");
        cliente3.setEndereco("Endereco");
        cliente3.setId(123L);
        cliente3.setNome("Nome");
        cliente3.setTelefone("Telefone");

        Conta conta3 = new Conta();
        conta3.setAgencia("Agencia");
        conta3.setCliente(cliente3);
        conta3.setDataCriacao("Data Criacao");
        conta3.setId(123L);
        conta3.setNumeroDaConta("Numero Da Conta");
        conta3.setOperacoes(new ArrayList<>());
        conta3.setSaldo(BigDecimal.valueOf(42L));
        conta3.setSaqueSemTaxa(1);
        conta3.setTaxa(BigDecimal.valueOf(42L));
        conta3.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenReturn(conta3);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        doThrow(new OperacaoNaoCompletadaException()).when(operacaoProducer).enviar((Operacao) any());
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.depositar(operacaoDto));
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(contaRepository).existsBynumeroDaConta((String) any());
        verify(contaRepository).findBynumeroDaConta((String) any());
        verify(operacaoProducer).enviar((Operacao) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#depositar(OperacaoDto)}
     */
    @Test
    void testDepositar4()
            throws OperacaoNaoCompletadaException, InterruptedException, ExecutionException, TimeoutException {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        Operacao operacao1 = mock(Operacao.class);
        when(operacao1.getNumeroConta()).thenReturn("Numero Conta");
        when(operacao1.getValorTransacao()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(operacao1).setConta((Conta) any());
        doNothing().when(operacao1).setContaDestino((String) any());
        doNothing().when(operacao1).setId((Long) any());
        doNothing().when(operacao1).setNumeroConta((String) any());
        doNothing().when(operacao1).setSaldo((BigDecimal) any());
        doNothing().when(operacao1).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao1).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao1).setValorTransacao((BigDecimal) any());
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoDepositoResponse operacaoDepositoResponse = new OperacaoDepositoResponse();
        operacaoDepositoResponse.setMensagem("Mensagem");
        operacaoDepositoResponse.setNumeroConta("Numero Conta");
        operacaoDepositoResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoDepositoResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toResponse((Operacao) any())).thenReturn(operacaoDepositoResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);

        Cliente cliente3 = new Cliente();
        cliente3.setCnpj("Cnpj");
        cliente3.setContas(new ArrayList<>());
        cliente3.setCpf("Cpf");
        cliente3.setEndereco("Endereco");
        cliente3.setId(123L);
        cliente3.setNome("Nome");
        cliente3.setTelefone("Telefone");

        Conta conta3 = new Conta();
        conta3.setAgencia("Agencia");
        conta3.setCliente(cliente3);
        conta3.setDataCriacao("Data Criacao");
        conta3.setId(123L);
        conta3.setNumeroDaConta("Numero Da Conta");
        conta3.setOperacoes(new ArrayList<>());
        conta3.setSaldo(BigDecimal.valueOf(42L));
        conta3.setSaqueSemTaxa(1);
        conta3.setTaxa(BigDecimal.valueOf(42L));
        conta3.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenReturn(conta3);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        doNothing().when(operacaoProducer).enviar((Operacao) any());
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        OperacaoDepositoResponse actualDepositarResult = operacaoServiceImpl.depositar(operacaoDto);
        assertSame(operacaoDepositoResponse, actualDepositarResult);
        assertEquals("42", actualDepositarResult.getValorTransacao().toString());
        verify(operacaoRepository).save((Operacao) any());
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toResponse((Operacao) any());
        verify(operacao1).getNumeroConta();
        verify(operacao1).getValorTransacao();
        verify(operacao1, atLeast(1)).setConta((Conta) any());
        verify(operacao1).setContaDestino((String) any());
        verify(operacao1).setId((Long) any());
        verify(operacao1).setNumeroConta((String) any());
        verify(operacao1, atLeast(1)).setSaldo((BigDecimal) any());
        verify(operacao1).setTaxaDeTransferencia((BigDecimal) any());
        verify(operacao1, atLeast(1)).setTipoDeOperacao((OperacaoEnum) any());
        verify(operacao1).setValorTransacao((BigDecimal) any());
        verify(contaRepository).existsBynumeroDaConta((String) any());
        verify(contaRepository).findBynumeroDaConta((String) any());
        verify(contaRepository).save((Conta) any());
        verify(operacaoProducer).enviar((Operacao) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSacar() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.math.BigDecimal.doubleValue()" because the return value of "com.desafioProject.Cliente.viewer.dto.request.OperacaoDto.getValorTransacao()" is null
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.ifValorMenorIgualZero(OperacaoServiceImpl.java:184)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.sacar(OperacaoServiceImpl.java:88)
        //   In order to prevent sacar(OperacaoDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   sacar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        operacaoServiceImpl.sacar(new OperacaoDto());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSacar2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   redis.clients.jedis.exceptions.JedisConnectionException: Failed to connect to any host resolved for DNS name.
        //       at redis.clients.jedis.DefaultJedisSocketFactory.connectToFirstSuccessfulHost(DefaultJedisSocketFactory.java:63)
        //       at redis.clients.jedis.DefaultJedisSocketFactory.createSocket(DefaultJedisSocketFactory.java:87)
        //       at redis.clients.jedis.Connection.connect(Connection.java:180)
        //       at redis.clients.jedis.Connection.sendCommand(Connection.java:152)
        //       at redis.clients.jedis.Connection.executeCommand(Connection.java:121)
        //       at redis.clients.jedis.Jedis.get(Jedis.java:4901)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.sacar(OperacaoServiceImpl.java:108)
        //   In order to prevent sacar(OperacaoDto)
        //   from throwing JedisConnectionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   sacar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoSaqueResponse operacaoSaqueResponse = new OperacaoSaqueResponse();
        operacaoSaqueResponse.setMensagem("Mensagem");
        operacaoSaqueResponse.setNumeroConta("Numero Conta");
        operacaoSaqueResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoSaqueResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenReturn(operacaoSaqueResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        operacaoServiceImpl.sacar(operacaoDto);
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    void testSacar3() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenThrow(new OperacaoNaoCompletadaException());
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.sacar(operacaoDto));
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toSaqueResponse((Operacao) any());
        verify(contaRepository).existsBynumeroDaConta((String) any());
        verify(contaRepository).findBynumeroDaConta((String) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSacar4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   redis.clients.jedis.exceptions.JedisConnectionException: Failed to connect to any host resolved for DNS name.
        //       at redis.clients.jedis.DefaultJedisSocketFactory.connectToFirstSuccessfulHost(DefaultJedisSocketFactory.java:63)
        //       at redis.clients.jedis.DefaultJedisSocketFactory.createSocket(DefaultJedisSocketFactory.java:87)
        //       at redis.clients.jedis.Connection.connect(Connection.java:180)
        //       at redis.clients.jedis.Connection.sendCommand(Connection.java:152)
        //       at redis.clients.jedis.Connection.executeCommand(Connection.java:121)
        //       at redis.clients.jedis.Jedis.get(Jedis.java:4901)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.sacar(OperacaoServiceImpl.java:108)
        //   In order to prevent sacar(OperacaoDto)
        //   from throwing JedisConnectionException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   sacar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);
        Operacao operacao = mock(Operacao.class);
        doNothing().when(operacao).setConta((Conta) any());
        doNothing().when(operacao).setContaDestino((String) any());
        doNothing().when(operacao).setId((Long) any());
        doNothing().when(operacao).setNumeroConta((String) any());
        doNothing().when(operacao).setSaldo((BigDecimal) any());
        doNothing().when(operacao).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao).setValorTransacao((BigDecimal) any());
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoSaqueResponse operacaoSaqueResponse = new OperacaoSaqueResponse();
        operacaoSaqueResponse.setMensagem("Mensagem");
        operacaoSaqueResponse.setNumeroConta("Numero Conta");
        operacaoSaqueResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoSaqueResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenReturn(operacaoSaqueResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        operacaoServiceImpl.sacar(operacaoDto);
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    void testSacar5() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);
        Operacao operacao = mock(Operacao.class);
        doNothing().when(operacao).setConta((Conta) any());
        doNothing().when(operacao).setContaDestino((String) any());
        doNothing().when(operacao).setId((Long) any());
        doNothing().when(operacao).setNumeroConta((String) any());
        doNothing().when(operacao).setSaldo((BigDecimal) any());
        doNothing().when(operacao).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao).setValorTransacao((BigDecimal) any());
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoSaqueResponse operacaoSaqueResponse = new OperacaoSaqueResponse();
        operacaoSaqueResponse.setMensagem("Mensagem");
        operacaoSaqueResponse.setNumeroConta("Numero Conta");
        operacaoSaqueResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoSaqueResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenReturn(operacaoSaqueResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        Conta conta1 = mock(Conta.class);
        when(conta1.getNumeroDaConta()).thenThrow(new OperacaoNaoCompletadaException());
        when(conta1.getTipo()).thenReturn(TipoDeConta.Fisica);
        when(conta1.getTaxa()).thenReturn(BigDecimal.valueOf(42L));
        when(conta1.getSaldo()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(conta1).setAgencia((String) any());
        doNothing().when(conta1).setCliente((Cliente) any());
        doNothing().when(conta1).setDataCriacao((String) any());
        doNothing().when(conta1).setId((Long) any());
        doNothing().when(conta1).setNumeroDaConta((String) any());
        doNothing().when(conta1).setOperacoes((List<Operacao>) any());
        doNothing().when(conta1).setSaldo((BigDecimal) any());
        doNothing().when(conta1).setSaqueSemTaxa(anyInt());
        doNothing().when(conta1).setTaxa((BigDecimal) any());
        doNothing().when(conta1).setTipo((TipoDeConta) any());
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.sacar(operacaoDto));
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toSaqueResponse((Operacao) any());
        verify(operacao, atLeast(1)).setConta((Conta) any());
        verify(operacao).setContaDestino((String) any());
        verify(operacao).setId((Long) any());
        verify(operacao).setNumeroConta((String) any());
        verify(operacao).setSaldo((BigDecimal) any());
        verify(operacao).setTaxaDeTransferencia((BigDecimal) any());
        verify(operacao, atLeast(1)).setTipoDeOperacao((OperacaoEnum) any());
        verify(operacao).setValorTransacao((BigDecimal) any());
        verify(contaRepository).existsBynumeroDaConta((String) any());
        verify(contaRepository).findBynumeroDaConta((String) any());
        verify(conta1).getTipo();
        verify(conta1).getNumeroDaConta();
        verify(conta1, atLeast(1)).getSaldo();
        verify(conta1).getTaxa();
        verify(conta1).setAgencia((String) any());
        verify(conta1).setCliente((Cliente) any());
        verify(conta1).setDataCriacao((String) any());
        verify(conta1).setId((Long) any());
        verify(conta1).setNumeroDaConta((String) any());
        verify(conta1).setOperacoes((List<Operacao>) any());
        verify(conta1).setSaldo((BigDecimal) any());
        verify(conta1).setSaqueSemTaxa(anyInt());
        verify(conta1).setTaxa((BigDecimal) any());
        verify(conta1).setTipo((TipoDeConta) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSacar6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot read field "intCompact" because "augend" is null
        //       at java.math.BigDecimal.add(BigDecimal.java:1368)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.sacar(OperacaoServiceImpl.java:102)
        //   In order to prevent sacar(OperacaoDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   sacar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);
        Operacao operacao = mock(Operacao.class);
        doNothing().when(operacao).setConta((Conta) any());
        doNothing().when(operacao).setContaDestino((String) any());
        doNothing().when(operacao).setId((Long) any());
        doNothing().when(operacao).setNumeroConta((String) any());
        doNothing().when(operacao).setSaldo((BigDecimal) any());
        doNothing().when(operacao).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao).setValorTransacao((BigDecimal) any());
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoSaqueResponse operacaoSaqueResponse = new OperacaoSaqueResponse();
        operacaoSaqueResponse.setMensagem("Mensagem");
        operacaoSaqueResponse.setNumeroConta("Numero Conta");
        operacaoSaqueResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoSaqueResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenReturn(operacaoSaqueResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        Conta conta1 = mock(Conta.class);
        when(conta1.getNumeroDaConta()).thenReturn("Numero Da Conta");
        when(conta1.getTipo()).thenReturn(TipoDeConta.Fisica);
        when(conta1.getTaxa()).thenReturn(null);
        when(conta1.getSaldo()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(conta1).setAgencia((String) any());
        doNothing().when(conta1).setCliente((Cliente) any());
        doNothing().when(conta1).setDataCriacao((String) any());
        doNothing().when(conta1).setId((Long) any());
        doNothing().when(conta1).setNumeroDaConta((String) any());
        doNothing().when(conta1).setOperacoes((List<Operacao>) any());
        doNothing().when(conta1).setSaldo((BigDecimal) any());
        doNothing().when(conta1).setSaqueSemTaxa(anyInt());
        doNothing().when(conta1).setTaxa((BigDecimal) any());
        doNothing().when(conta1).setTipo((TipoDeConta) any());
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        operacaoServiceImpl.sacar(operacaoDto);
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#sacar(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSacar7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.math.BigDecimal.doubleValue()" because the return value of "com.desafioProject.Cliente.model.entity.Conta.getSaldo()" is null
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.sacar(OperacaoServiceImpl.java:94)
        //   In order to prevent sacar(OperacaoDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   sacar(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);
        Operacao operacao = mock(Operacao.class);
        doNothing().when(operacao).setConta((Conta) any());
        doNothing().when(operacao).setContaDestino((String) any());
        doNothing().when(operacao).setId((Long) any());
        doNothing().when(operacao).setNumeroConta((String) any());
        doNothing().when(operacao).setSaldo((BigDecimal) any());
        doNothing().when(operacao).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao).setValorTransacao((BigDecimal) any());
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoSaqueResponse operacaoSaqueResponse = new OperacaoSaqueResponse();
        operacaoSaqueResponse.setMensagem("Mensagem");
        operacaoSaqueResponse.setNumeroConta("Numero Conta");
        operacaoSaqueResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoSaqueResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toSaqueResponse((Operacao) any())).thenReturn(operacaoSaqueResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        Conta conta1 = mock(Conta.class);
        when(conta1.getNumeroDaConta()).thenReturn("Numero Da Conta");
        when(conta1.getTipo()).thenReturn(TipoDeConta.Fisica);
        when(conta1.getTaxa()).thenReturn(BigDecimal.valueOf(42L));
        when(conta1.getSaldo()).thenReturn(null);
        doNothing().when(conta1).setAgencia((String) any());
        doNothing().when(conta1).setCliente((Cliente) any());
        doNothing().when(conta1).setDataCriacao((String) any());
        doNothing().when(conta1).setId((Long) any());
        doNothing().when(conta1).setNumeroDaConta((String) any());
        doNothing().when(conta1).setOperacoes((List<Operacao>) any());
        doNothing().when(conta1).setSaldo((BigDecimal) any());
        doNothing().when(conta1).setSaqueSemTaxa(anyInt());
        doNothing().when(conta1).setTaxa((BigDecimal) any());
        doNothing().when(conta1).setTipo((TipoDeConta) any());
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta1);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        operacaoServiceImpl.sacar(operacaoDto);
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#transferir(OperacaoDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testTransferir() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.math.BigDecimal.doubleValue()" because the return value of "com.desafioProject.Cliente.viewer.dto.request.OperacaoDto.getValorTransacao()" is null
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.ifValorMenorIgualZero(OperacaoServiceImpl.java:184)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.transferir(OperacaoServiceImpl.java:128)
        //   In order to prevent transferir(OperacaoDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   transferir(OperacaoDto).
        //   See https://diff.blue/R013 to resolve this issue.

        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        operacaoServiceImpl.transferir(new OperacaoDto());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#transferir(OperacaoDto)}
     */
    @Test
    void testTransferir2() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);

        Operacao operacao1 = new Operacao();
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoTransfResponse operacaoTransfResponse = new OperacaoTransfResponse();
        operacaoTransfResponse.setContaDestino("Conta Destino");
        operacaoTransfResponse.setMensagem("Mensagem");
        operacaoTransfResponse.setNumeroConta("Numero Conta");
        operacaoTransfResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoTransfResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toTransfResponse((Operacao) any())).thenReturn(operacaoTransfResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);

        Cliente cliente3 = new Cliente();
        cliente3.setCnpj("Cnpj");
        cliente3.setContas(new ArrayList<>());
        cliente3.setCpf("Cpf");
        cliente3.setEndereco("Endereco");
        cliente3.setId(123L);
        cliente3.setNome("Nome");
        cliente3.setTelefone("Telefone");

        Conta conta3 = new Conta();
        conta3.setAgencia("Agencia");
        conta3.setCliente(cliente3);
        conta3.setDataCriacao("Data Criacao");
        conta3.setId(123L);
        conta3.setNumeroDaConta("Numero Da Conta");
        conta3.setOperacoes(new ArrayList<>());
        conta3.setSaldo(BigDecimal.valueOf(42L));
        conta3.setSaqueSemTaxa(1);
        conta3.setTaxa(BigDecimal.valueOf(42L));
        conta3.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenReturn(conta3);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        OperacaoTransfResponse actualTransferirResult = operacaoServiceImpl.transferir(operacaoDto);
        assertSame(operacaoTransfResponse, actualTransferirResult);
        assertEquals("42", actualTransferirResult.getValorTransacao().toString());
        verify(operacaoRepository).save((Operacao) any());
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toTransfResponse((Operacao) any());
        verify(contaRepository, atLeast(1)).existsBynumeroDaConta((String) any());
        verify(contaRepository, atLeast(1)).findBynumeroDaConta((String) any());
        verify(contaRepository, atLeast(1)).save((Conta) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#transferir(OperacaoDto)}
     */
    @Test
    void testTransferir3() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);

        Operacao operacao1 = new Operacao();
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoTransfResponse operacaoTransfResponse = new OperacaoTransfResponse();
        operacaoTransfResponse.setContaDestino("Conta Destino");
        operacaoTransfResponse.setMensagem("Mensagem");
        operacaoTransfResponse.setNumeroConta("Numero Conta");
        operacaoTransfResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoTransfResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toTransfResponse((Operacao) any())).thenReturn(operacaoTransfResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenThrow(new OperacaoNaoCompletadaException());
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.transferir(operacaoDto));
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(contaRepository, atLeast(1)).existsBynumeroDaConta((String) any());
        verify(contaRepository, atLeast(1)).findBynumeroDaConta((String) any());
        verify(contaRepository).save((Conta) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#transferir(OperacaoDto)}
     */
    @Test
    void testTransferir4() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        when(operacaoRepository.save((Operacao) any())).thenReturn(operacao);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        Conta conta1 = new Conta();
        conta1.setAgencia("Agencia");
        conta1.setCliente(cliente1);
        conta1.setDataCriacao("Data Criacao");
        conta1.setId(123L);
        conta1.setNumeroDaConta("Numero Da Conta");
        conta1.setOperacoes(new ArrayList<>());
        conta1.setSaldo(BigDecimal.valueOf(42L));
        conta1.setSaqueSemTaxa(1);
        conta1.setTaxa(BigDecimal.valueOf(42L));
        conta1.setTipo(TipoDeConta.Fisica);
        Operacao operacao1 = mock(Operacao.class);
        when(operacao1.getContaDestino()).thenReturn("Conta Destino");
        when(operacao1.getNumeroConta()).thenReturn("Numero Conta");
        when(operacao1.getValorTransacao()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(operacao1).setConta((Conta) any());
        doNothing().when(operacao1).setContaDestino((String) any());
        doNothing().when(operacao1).setId((Long) any());
        doNothing().when(operacao1).setNumeroConta((String) any());
        doNothing().when(operacao1).setSaldo((BigDecimal) any());
        doNothing().when(operacao1).setTaxaDeTransferencia((BigDecimal) any());
        doNothing().when(operacao1).setTipoDeOperacao((OperacaoEnum) any());
        doNothing().when(operacao1).setValorTransacao((BigDecimal) any());
        operacao1.setConta(conta1);
        operacao1.setContaDestino("Conta Destino");
        operacao1.setId(123L);
        operacao1.setNumeroConta("Numero Conta");
        operacao1.setSaldo(BigDecimal.valueOf(42L));
        operacao1.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao1.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao1.setValorTransacao(BigDecimal.valueOf(42L));

        OperacaoTransfResponse operacaoTransfResponse = new OperacaoTransfResponse();
        operacaoTransfResponse.setContaDestino("Conta Destino");
        operacaoTransfResponse.setMensagem("Mensagem");
        operacaoTransfResponse.setNumeroConta("Numero Conta");
        operacaoTransfResponse.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacaoTransfResponse.setValorTransacao(BigDecimal.valueOf(42L));
        when(mapperOperacao.toTransfResponse((Operacao) any())).thenReturn(operacaoTransfResponse);
        when(mapperOperacao.toModel((OperacaoDto) any())).thenReturn(operacao1);

        Cliente cliente2 = new Cliente();
        cliente2.setCnpj("Cnpj");
        cliente2.setContas(new ArrayList<>());
        cliente2.setCpf("Cpf");
        cliente2.setEndereco("Endereco");
        cliente2.setId(123L);
        cliente2.setNome("Nome");
        cliente2.setTelefone("Telefone");

        Conta conta2 = new Conta();
        conta2.setAgencia("Agencia");
        conta2.setCliente(cliente2);
        conta2.setDataCriacao("Data Criacao");
        conta2.setId(123L);
        conta2.setNumeroDaConta("Numero Da Conta");
        conta2.setOperacoes(new ArrayList<>());
        conta2.setSaldo(BigDecimal.valueOf(42L));
        conta2.setSaqueSemTaxa(1);
        conta2.setTaxa(BigDecimal.valueOf(42L));
        conta2.setTipo(TipoDeConta.Fisica);

        Cliente cliente3 = new Cliente();
        cliente3.setCnpj("Cnpj");
        cliente3.setContas(new ArrayList<>());
        cliente3.setCpf("Cpf");
        cliente3.setEndereco("Endereco");
        cliente3.setId(123L);
        cliente3.setNome("Nome");
        cliente3.setTelefone("Telefone");

        Conta conta3 = new Conta();
        conta3.setAgencia("Agencia");
        conta3.setCliente(cliente3);
        conta3.setDataCriacao("Data Criacao");
        conta3.setId(123L);
        conta3.setNumeroDaConta("Numero Da Conta");
        conta3.setOperacoes(new ArrayList<>());
        conta3.setSaldo(BigDecimal.valueOf(42L));
        conta3.setSaqueSemTaxa(1);
        conta3.setTaxa(BigDecimal.valueOf(42L));
        conta3.setTipo(TipoDeConta.Fisica);
        when(contaRepository.save((Conta) any())).thenReturn(conta3);
        when(contaRepository.findBynumeroDaConta((String) any())).thenReturn(conta2);
        when(contaRepository.existsBynumeroDaConta((String) any())).thenReturn(true);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        valueOfResult.add(BigDecimal.valueOf(42L));

        OperacaoDto operacaoDto = new OperacaoDto();
        operacaoDto.setValorTransacao(valueOfResult);
        OperacaoTransfResponse actualTransferirResult = operacaoServiceImpl.transferir(operacaoDto);
        assertSame(operacaoTransfResponse, actualTransferirResult);
        assertEquals("42", actualTransferirResult.getValorTransacao().toString());
        verify(operacaoRepository).save((Operacao) any());
        verify(mapperOperacao).toModel((OperacaoDto) any());
        verify(mapperOperacao).toTransfResponse((Operacao) any());
        verify(operacao1).getContaDestino();
        verify(operacao1).getNumeroConta();
        verify(operacao1).getValorTransacao();
        verify(operacao1, atLeast(1)).setConta((Conta) any());
        verify(operacao1).setContaDestino((String) any());
        verify(operacao1).setId((Long) any());
        verify(operacao1).setNumeroConta((String) any());
        verify(operacao1, atLeast(1)).setSaldo((BigDecimal) any());
        verify(operacao1).setTaxaDeTransferencia((BigDecimal) any());
        verify(operacao1, atLeast(1)).setTipoDeOperacao((OperacaoEnum) any());
        verify(operacao1).setValorTransacao((BigDecimal) any());
        verify(contaRepository, atLeast(1)).existsBynumeroDaConta((String) any());
        verify(contaRepository, atLeast(1)).findBynumeroDaConta((String) any());
        verify(contaRepository, atLeast(1)).save((Conta) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#deletar(Long)}
     */
    @Test
    void testDeletar() {
        doNothing().when(operacaoRepository).deleteById((Long) any());
        operacaoServiceImpl.deletar(123L);
        verify(operacaoRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#deletar(Long)}
     */
    @Test
    void testDeletar2() {
        doThrow(new OperacaoNaoCompletadaException()).when(operacaoRepository).deleteById((Long) any());
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.deletar(123L));
        verify(operacaoRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#findById(Long)}
     */
    @Test
    void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        Optional<Operacao> ofResult = Optional.of(operacao);
        when(operacaoRepository.findById((Long) any())).thenReturn(ofResult);
        OperacaoDto operacaoDto = new OperacaoDto();
        when(mapperOperacao.toDto((Operacao) any())).thenReturn(operacaoDto);
        assertSame(operacaoDto, operacaoServiceImpl.findById(123L));
        verify(operacaoRepository).findById((Long) any());
        verify(mapperOperacao).toDto((Operacao) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#findById(Long)}
     */
    @Test
    void testFindById2() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");

        Conta conta = new Conta();
        conta.setAgencia("Agencia");
        conta.setCliente(cliente);
        conta.setDataCriacao("Data Criacao");
        conta.setId(123L);
        conta.setNumeroDaConta("Numero Da Conta");
        conta.setOperacoes(new ArrayList<>());
        conta.setSaldo(BigDecimal.valueOf(42L));
        conta.setSaqueSemTaxa(1);
        conta.setTaxa(BigDecimal.valueOf(42L));
        conta.setTipo(TipoDeConta.Fisica);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setContaDestino("Conta Destino");
        operacao.setId(123L);
        operacao.setNumeroConta("Numero Conta");
        operacao.setSaldo(BigDecimal.valueOf(42L));
        operacao.setTaxaDeTransferencia(BigDecimal.valueOf(42L));
        operacao.setTipoDeOperacao(OperacaoEnum.TRANSFERENCIA);
        operacao.setValorTransacao(BigDecimal.valueOf(42L));
        Optional<Operacao> ofResult = Optional.of(operacao);
        when(operacaoRepository.findById((Long) any())).thenReturn(ofResult);
        when(mapperOperacao.toDto((Operacao) any())).thenThrow(new OperacaoNaoCompletadaException());
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.findById(123L));
        verify(operacaoRepository).findById((Long) any());
        verify(mapperOperacao).toDto((Operacao) any());
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#findById(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindById3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.desafioProject.Cliente.viewer.exception.OperacaoNotFoundException
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.desafioProject.Cliente.model.service.implement.OperacaoServiceImpl.findById(OperacaoServiceImpl.java:168)
        //   In order to prevent findById(Long)
        //   from throwing OperacaoNotFoundException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findById(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(operacaoRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mapperOperacao.toDto((Operacao) any())).thenReturn(new OperacaoDto());
        operacaoServiceImpl.findById(123L);
    }

    /**
     * Method under test: {@link OperacaoServiceImpl#listar()}
     */
    @Test
    void testListar() {
        when(operacaoRepository.findAll()).thenThrow(new OperacaoNaoCompletadaException());
        assertThrows(OperacaoNaoCompletadaException.class, () -> operacaoServiceImpl.listar());
        verify(operacaoRepository).findAll();
    }
}

