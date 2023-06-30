package com.desafioproject.cliente.model.service;

import com.desafioproject.cliente.model.entity.Cliente;
import com.desafioproject.cliente.model.repository.ClienteRepository;
import com.desafioproject.cliente.model.service.implement.ClienteServiceImpl;
import com.desafioproject.cliente.utils.ClienteBuilder;
import com.desafioproject.cliente.utils.ClienteDtoBuilder;
import com.desafioproject.cliente.utils.ClienteResponseBuilder;
import com.desafioproject.cliente.viewer.dto.request.ClienteDto;
import com.desafioproject.cliente.viewer.dto.response.ClienteResponse;
import com.desafioproject.cliente.viewer.exception.ClienteDocumentoNotBeNullException;
import com.desafioproject.cliente.viewer.exception.ClienteExistsException;
import com.desafioproject.cliente.viewer.exception.ClienteNotFoundException;
import com.desafioproject.cliente.viewer.mappers.MapperCliente;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ClienteServiceImpl.class)
public class ClienteServiceTest {

    @Autowired
    ClienteServiceImpl clienteService;

    @MockBean
    MapperCliente mapperCliente;

    @MockBean
    ClienteRepository repository;

    @Test
    @DisplayName("Deve salvar um cliente")
    void clienteSalvoTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCpf().criar();
        ClienteResponse clienteResponse = ClienteResponseBuilder.criarResponse().criar();

        when(repository.existsBycpf(Mockito.anyString())).thenReturn(false);
        when(repository.save(cliente)).thenReturn(cliente);

        when(clienteService.salvar(clienteDto)).thenReturn(clienteResponse);

        assertThat(clienteResponse.getNome()).isNotNull();
        assertThat(clienteResponse.getNome()).isEqualTo("José");
        assertThat(clienteResponse.getMensagem()).isEqualTo("Cliente criado com sucesso!");
    }

    @Test
    @DisplayName("Devem obter um cliente pelo cnpj")
    void clienteByCnpjTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();

        repository.save(cliente);
        when(repository.findBycnpj(clienteDto.getCnpj())).thenReturn(cliente);

        assertThat(cliente.getId()).isEqualTo(1L);
        assertThat(cliente.getCnpj()).isEqualTo("70.794.651/0001-86");
        assertThat(cliente.getNome()).isEqualTo("José");
        assertThat(cliente.getCpf()).isEqualTo("149.945.810-02");
        assertThat(cliente.getTelefone()).isEqualTo("(11) 98521-1715");
    }

    @Test
    @DisplayName("Devem localizar um cliente pelo cnpj")
    void localizarClienteByCnpjTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();

        repository.save(cliente);
        clienteService.salvar(clienteDto);
        when(repository.findBycnpj(clienteDto.getCnpj())).thenReturn(cliente);
        clienteService.localizarCnpj(clienteDto.getCnpj());

        assertThat(clienteDto.getCnpj()).isEqualTo("70.794.651/0001-86");
        assertThat(clienteDto.getNome()).isEqualTo("José");
        assertThat(clienteDto.getCpf()).isEqualTo("149.945.810-02");
        assertThat(clienteDto.getTelefone()).isEqualTo("(11) 98521-1715");
    }

    @Test
    @DisplayName("Devem obter um cliente pelo ID")
    void pesquisaClientePorIDTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();

        repository.save(cliente);
        when(repository.findById(clienteDto.getId())).thenReturn(Optional.of(cliente));

        assertThat(cliente.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um cliente com cpf null")
    void estouraExceptionSeClienteTiverCpfInvalido() {
        //cenario
        Cliente cliente = ClienteBuilder.criarClienteSemDados().criar();
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDtoSemDados().criar();
        when(repository.findBycpf(cliente.getCpf())).thenThrow(ClienteDocumentoNotBeNullException.class);

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> clienteService.salvar(clienteDto));

        //verificacoes
        assertThat(exception).isNotNull();

    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um cliente com cpf existente")
    void estouraExceptionSeClienteTiverCpfExistente() {
        //cenario
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        when(repository.existsBycpf(cliente.getCpf())).thenThrow(ClienteExistsException.class);

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> clienteService.salvar(clienteDto));

        //verificacoes
        assertThat(exception).isNotNull();

    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um cliente com cnpj existente")
    void estouraExceptionSeClienteTiverCnpjExistente() {
        //cenario
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        when(repository.existsBycnpj(cliente.getCnpj())).thenThrow(ClienteExistsException.class);

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> clienteService.salvar(clienteDto));

        //verificacoes
        assertThat(exception).isNull();

    }

    @Test
    @DisplayName("Deve retornar exception ao obter um cliente por Id quando ele não existe na base.")
    void clienteNaoEncontradoPorId() throws ClienteNotFoundException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(1L);

        when(repository.findById(clienteDto.getId())).thenReturn(Optional.empty()).thenThrow(ClienteNotFoundException.class);

        //execucao
        try {
            clienteService.findById(clienteDto.getId());
        } catch (Exception e) {
            assertThrows(ClienteNotFoundException.class, () -> clienteService.findById(clienteDto.getId()));
        }
    }

    @Test
    @DisplayName("Deve deletar um cliente.")
    void deletarClienteTest() {
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();
        repository.save(cliente);

        //execucao
        assertDoesNotThrow(() -> clienteService.deleteById(cliente.getId()));

        //verificacoes
        Mockito.verify(repository, Mockito.times(1)).deleteById(cliente.getId());
    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar deletar um cliente inexistente.")
    void deletarClienteInvalidoTest() {
        Cliente cliente = new Cliente();

        try {
            clienteService.deleteById(1L);
        } catch (Exception e) {
            assertThrows(ClienteNotFoundException.class, ()-> clienteService.deleteById(cliente.getId()));
        }

        Mockito.verify(repository, Mockito.never()).delete(cliente);
    }

    @Test
    @DisplayName("Caso cliente não preencha nenhum dos documentos, estourará uma exception")
    void clienteComCnpjNuloEstouraExceptionTest() {
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();

        try {
            clienteService.localizarCnpj(cliente.getCnpj());
        } catch (Exception e) {
            assertThrows(ClienteNotFoundException.class, () -> clienteService.localizarCnpj(cliente.getCnpj()),
                    "Cliente não foi encontrado");
        }
        try {
            clienteService.localizarCpf(cliente.getCpf());
        } catch (Exception e) {
            assertThrows(ClienteNotFoundException.class, () -> clienteService.localizarCpf(cliente.getCpf()),
                    "Cliente não foi encontrado");
        }
    }

    @Test
    @DisplayName("Teste de exception que não faço a menor ideia do por que ela está funcionando")
    void clienteEstouraExceptionComDadosNullTest() throws ClienteExistsException {
        ClienteDto cliente = new ClienteDto();
        cliente.setCpf(null);
        cliente.setCnpj(null);

        when(repository.existsBycpf(Mockito.eq(null))).thenThrow(ClienteExistsException.class);
        when(repository.existsBycnpj(Mockito.eq(null))).thenThrow(ClienteExistsException.class);

        assertThrows(ClienteNotFoundException.class, () -> clienteService.localizarCpf(cliente.getCpf()));
        assertThrows(ClienteNotFoundException.class, () -> clienteService.localizarCnpj(cliente.getCnpj()));
    }

    @Test
    void testeDeErroPraVerSeFuncionaAExceptionQueNaoSeiComoFunciona() {
        Cliente cliente = new Cliente();
        cliente.setCpf(null);

        assertThrows(ClienteNotFoundException.class, () -> clienteService.localizarCpf(cliente.getCpf()), "Cliente não foi encontrado");
    }
}
