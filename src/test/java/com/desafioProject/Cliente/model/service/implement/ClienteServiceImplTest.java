package com.desafioProject.Cliente.model.service.implement;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.repository.ClienteRepository;
import com.desafioProject.Cliente.viewer.dto.request.ClienteDto;
import com.desafioProject.Cliente.viewer.dto.response.ClienteResponse;
import com.desafioProject.Cliente.viewer.exception.ClienteDocumentoNotBeNullException;
import com.desafioProject.Cliente.viewer.exception.ClienteExistsException;
import com.desafioProject.Cliente.viewer.exception.ClienteNotFoundException;
import com.desafioProject.Cliente.viewer.mappers.MapperCliente;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClienteServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClienteServiceImplTest {
    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @MockBean
    private MapperCliente mapperCliente;

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar() {
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(true);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);
        assertThrows(ClienteExistsException.class, () -> clienteServiceImpl.salvar(new ClienteDto()));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar2() {
        when(clienteRepository.existsBycnpj((String) any())).thenThrow(new ClienteNotFoundException());
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.salvar(new ClienteDto()));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar3() {
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(false);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);
        assertThrows(ClienteDocumentoNotBeNullException.class, () -> clienteServiceImpl.salvar(new ClienteDto()));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar4() {
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(true);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(false);
        assertThrows(ClienteDocumentoNotBeNullException.class, () -> clienteServiceImpl.salvar(new ClienteDto()));
        verify(clienteRepository).existsBycpf((String) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSalvar5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.desafioProject.Cliente.viewer.dto.request.ClienteDto.getCpf()" because "clienteDto" is null
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.ifExisteByCpfOuCnpj(ClienteServiceImpl.java:105)
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.salvar(ClienteServiceImpl.java:29)
        //   In order to prevent salvar(ClienteDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   salvar(ClienteDto).
        //   See https://diff.blue/R013 to resolve this issue.

        when(clienteRepository.existsBycnpj((String) any())).thenReturn(true);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);
        clienteServiceImpl.salvar(null);
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar6() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(false);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setMensagem("Mensagem");
        clienteResponse.setNome("Nome");
        when(mapperCliente.toModel((ClienteDto) any())).thenReturn(cliente1);
        when(mapperCliente.toResponse((Cliente) any())).thenReturn(clienteResponse);

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setCpf("Cpf");
        assertSame(clienteResponse, clienteServiceImpl.salvar(clienteDto));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
        verify(clienteRepository).save((Cliente) any());
        verify(mapperCliente).toModel((ClienteDto) any());
        verify(mapperCliente).toResponse((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar7() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(false);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);
        when(mapperCliente.toModel((ClienteDto) any())).thenThrow(new ClienteDocumentoNotBeNullException());
        when(mapperCliente.toResponse((Cliente) any())).thenThrow(new ClienteDocumentoNotBeNullException());

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setCpf("Cpf");
        assertThrows(ClienteDocumentoNotBeNullException.class, () -> clienteServiceImpl.salvar(clienteDto));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
        verify(mapperCliente).toModel((ClienteDto) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#salvar(ClienteDto)}
     */
    @Test
    void testSalvar8() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);
        when(clienteRepository.existsBycnpj((String) any())).thenReturn(false);
        when(clienteRepository.existsBycpf((String) any())).thenReturn(true);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");

        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setMensagem("Mensagem");
        clienteResponse.setNome("Nome");
        when(mapperCliente.toModel((ClienteDto) any())).thenReturn(cliente1);
        when(mapperCliente.toResponse((Cliente) any())).thenReturn(clienteResponse);

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setCnpj("Cnpj");
        clienteDto.setCpf("Cpf");
        assertSame(clienteResponse, clienteServiceImpl.salvar(clienteDto));
        verify(clienteRepository).existsBycnpj((String) any());
        verify(clienteRepository).existsBycpf((String) any());
        verify(clienteRepository).save((Cliente) any());
        verify(mapperCliente).toModel((ClienteDto) any());
        verify(mapperCliente).toResponse((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#deleteById(Long)}
     */
    @Test
    void testDeleteById() {
        doNothing().when(clienteRepository).deleteById((Long) any());
        clienteServiceImpl.deleteById(123L);
        verify(clienteRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#deleteById(Long)}
     */
    @Test
    void testDeleteById2() {
        doThrow(new ClienteNotFoundException()).when(clienteRepository).deleteById((Long) any());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.deleteById(123L));
        verify(clienteRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#findById(Long)}
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
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        ClienteDto clienteDto = new ClienteDto();
        when(mapperCliente.toDto((Cliente) any())).thenReturn(clienteDto);
        assertSame(clienteDto, clienteServiceImpl.findById(123L));
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#findById(Long)}
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
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        when(mapperCliente.toDto((Cliente) any())).thenThrow(new ClienteNotFoundException());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.findById(123L));
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#findById(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindById3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.desafioProject.Cliente.viewer.exception.ClienteNotFoundException
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.findById(ClienteServiceImpl.java:43)
        //   In order to prevent findById(Long)
        //   from throwing ClienteNotFoundException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findById(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(clienteRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mapperCliente.toDto((Cliente) any())).thenReturn(new ClienteDto());
        clienteServiceImpl.findById(123L);
    }

    /**
     * Method under test: {@link ClienteServiceImpl#localizarCpf(String)}
     */
    @Test
    void testLocalizarCpf() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.findBycpf((String) any())).thenReturn(cliente);
        ClienteDto clienteDto = new ClienteDto();
        when(mapperCliente.toDto((Cliente) any())).thenReturn(clienteDto);
        assertSame(clienteDto, clienteServiceImpl.localizarCpf("Cpf"));
        verify(clienteRepository, atLeast(1)).findBycpf((String) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#localizarCpf(String)}
     */
    @Test
    void testLocalizarCpf2() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.findBycpf((String) any())).thenReturn(cliente);
        when(mapperCliente.toDto((Cliente) any())).thenThrow(new ClienteNotFoundException());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.localizarCpf("Cpf"));
        verify(clienteRepository, atLeast(1)).findBycpf((String) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#localizarCnpj(String)}
     */
    @Test
    void testLocalizarCnpj() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.findBycnpj((String) any())).thenReturn(cliente);
        ClienteDto clienteDto = new ClienteDto();
        when(mapperCliente.toDto((Cliente) any())).thenReturn(clienteDto);
        assertSame(clienteDto, clienteServiceImpl.localizarCnpj("Cnpj"));
        verify(clienteRepository, atLeast(1)).findBycnpj((String) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#localizarCnpj(String)}
     */
    @Test
    void testLocalizarCnpj2() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.findBycnpj((String) any())).thenReturn(cliente);
        when(mapperCliente.toDto((Cliente) any())).thenThrow(new ClienteNotFoundException());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.localizarCnpj("Cnpj"));
        verify(clienteRepository, atLeast(1)).findBycnpj((String) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar(ClienteDto, Long)}
     */
    @Test
    void testAtualizar() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        Optional<Cliente> ofResult = Optional.of(cliente);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente1);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        ClienteDto clienteDto = new ClienteDto();
        when(mapperCliente.toDto((Cliente) any())).thenReturn(clienteDto);
        doNothing().when(mapperCliente).atualizar((ClienteDto) any(), (Cliente) any());
        assertSame(clienteDto, clienteServiceImpl.atualizar(new ClienteDto(), 123L));
        verify(clienteRepository).save((Cliente) any());
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).toDto((Cliente) any());
        verify(mapperCliente).atualizar((ClienteDto) any(), (Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar(ClienteDto, Long)}
     */
    @Test
    void testAtualizar2() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        Optional<Cliente> ofResult = Optional.of(cliente);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente1);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        when(mapperCliente.toDto((Cliente) any())).thenThrow(new ClienteDocumentoNotBeNullException());
        doThrow(new ClienteDocumentoNotBeNullException()).when(mapperCliente)
                .atualizar((ClienteDto) any(), (Cliente) any());
        assertThrows(ClienteDocumentoNotBeNullException.class, () -> clienteServiceImpl.atualizar(new ClienteDto(), 123L));
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).atualizar((ClienteDto) any(), (Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar(ClienteDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAtualizar3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.desafioProject.Cliente.viewer.exception.ClienteNotFoundException
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.atualizar(ClienteServiceImpl.java:67)
        //   In order to prevent atualizar(ClienteDto, Long)
        //   from throwing ClienteNotFoundException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   atualizar(ClienteDto, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);
        when(clienteRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mapperCliente.toDto((Cliente) any())).thenReturn(new ClienteDto());
        doNothing().when(mapperCliente).atualizar((ClienteDto) any(), (Cliente) any());
        clienteServiceImpl.atualizar(new ClienteDto(), 123L);
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar2(ClienteDto, Long)}
     */
    @Test
    void testAtualizar22() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        Optional<Cliente> ofResult = Optional.of(cliente);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente1);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        ClienteDto clienteDto = new ClienteDto();
        when(mapperCliente.toDto((Cliente) any())).thenReturn(clienteDto);
        assertSame(clienteDto, clienteServiceImpl.atualizar2(new ClienteDto(), 123L));
        verify(clienteRepository).save((Cliente) any());
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar2(ClienteDto, Long)}
     */
    @Test
    void testAtualizar23() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        Optional<Cliente> ofResult = Optional.of(cliente);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente1);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        when(mapperCliente.toDto((Cliente) any())).thenThrow(new ClienteNotFoundException());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.atualizar2(new ClienteDto(), 123L));
        verify(clienteRepository).save((Cliente) any());
        verify(clienteRepository).findById((Long) any());
        verify(mapperCliente).toDto((Cliente) any());
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar2(ClienteDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAtualizar24() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.desafioProject.Cliente.viewer.exception.ClienteNotFoundException
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.atualizar2(ClienteServiceImpl.java:80)
        //   In order to prevent atualizar2(ClienteDto, Long)
        //   from throwing ClienteNotFoundException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   atualizar2(ClienteDto, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);
        when(clienteRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mapperCliente.toDto((Cliente) any())).thenReturn(new ClienteDto());
        clienteServiceImpl.atualizar2(new ClienteDto(), 123L);
    }

    /**
     * Method under test: {@link ClienteServiceImpl#atualizar2(ClienteDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAtualizar25() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.desafioProject.Cliente.viewer.dto.request.ClienteDto.getNome()" because "clienteDto" is null
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.lambda$atualizar2$1(ClienteServiceImpl.java:75)
        //       at java.util.Optional.map(Optional.java:260)
        //       at com.desafioProject.Cliente.model.service.implement.ClienteServiceImpl.atualizar2(ClienteServiceImpl.java:74)
        //   In order to prevent atualizar2(ClienteDto, Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   atualizar2(ClienteDto, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        Cliente cliente = new Cliente();
        cliente.setCnpj("Cnpj");
        cliente.setContas(new ArrayList<>());
        cliente.setCpf("Cpf");
        cliente.setEndereco("Endereco");
        cliente.setId(123L);
        cliente.setNome("Nome");
        cliente.setTelefone("Telefone");
        Optional<Cliente> ofResult = Optional.of(cliente);

        Cliente cliente1 = new Cliente();
        cliente1.setCnpj("Cnpj");
        cliente1.setContas(new ArrayList<>());
        cliente1.setCpf("Cpf");
        cliente1.setEndereco("Endereco");
        cliente1.setId(123L);
        cliente1.setNome("Nome");
        cliente1.setTelefone("Telefone");
        when(clienteRepository.save((Cliente) any())).thenReturn(cliente1);
        when(clienteRepository.findById((Long) any())).thenReturn(ofResult);
        when(mapperCliente.toDto((Cliente) any())).thenReturn(new ClienteDto());
        clienteServiceImpl.atualizar2(null, 123L);
    }

    /**
     * Method under test: {@link ClienteServiceImpl#listar()}
     */
    @Test
    void testListar() {
        when(clienteRepository.findAll()).thenThrow(new ClienteNotFoundException());
        assertThrows(ClienteNotFoundException.class, () -> clienteServiceImpl.listar());
        verify(clienteRepository).findAll();
    }
}

