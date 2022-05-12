package com.desafioProject.Cliente.controllers;

import com.desafioProject.Cliente.controller.ClienteController;
import com.desafioProject.Cliente.model.entity.Cliente;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import com.desafioProject.Cliente.model.repository.ClienteRepository;
import com.desafioProject.Cliente.model.service.ClienteService;
import com.desafioProject.Cliente.utils.ClienteBuilder;
import com.desafioProject.Cliente.utils.ClienteDtoBuilder;
import com.desafioProject.Cliente.utils.ClienteResponseBuilder;
import com.desafioProject.Cliente.viewer.dto.request.ClienteDto;
import com.desafioProject.Cliente.viewer.dto.response.ClienteResponse;
import com.desafioProject.Cliente.viewer.exception.ClienteNotBeNullException;
import com.desafioProject.Cliente.viewer.exception.ClienteNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc
public class ClienteControllerTest {

    static String ENDPOINT_URL = "/api/cliente";

    @Autowired
    MockMvc mvc;

    @MockBean
    ClienteService clienteService;

    @MockBean
    ClienteRepository repository;

    @Test
    @DisplayName("Deve criar um cliente com sucesso.")
    public void criarClienteRetornaDTOTest() throws Exception {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();

        ClienteResponse clienteResponse = ClienteResponseBuilder.criarResponse().criar();

        BDDMockito.given(clienteService.salvar(clienteDto)).willReturn(clienteResponse);

        String json = new ObjectMapper().writeValueAsString(clienteDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value(clienteDto.getNome()))
                .andExpect(jsonPath("mensagem").value(clienteResponse.getMensagem()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados insuficientes para criação do cliente")
    public void criarClienteInvalidoTest() throws Exception {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDtoSemDados().criar();

        String json = new ObjectMapper().writeValueAsString(clienteDto);

        MockHttpServletRequestBuilder request;

        request = MockMvcRequestBuilders
                .post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andReturn().getResolvedException();
    }

    @Test
    @DisplayName("Deve obter informações de cliente")
    public void recebeUmaListaDeClientePeloIDTest() throws Exception {
// Cenário (Given)
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();

        BDDMockito.given(clienteService.findById(clienteDto.getId())).willReturn(clienteDto);

//        Execução (When)
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ENDPOINT_URL.concat("/" + clienteDto.getId()))
                .accept(MediaType.APPLICATION_JSON);
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(clienteDto.getId()))
                .andExpect(jsonPath("nome").value(clienteDto.getNome()))
                .andExpect(jsonPath("cpf").value(clienteDto.getCpf()))
                .andExpect(jsonPath("cnpj").value(clienteDto.getCnpj()))
                .andExpect(jsonPath("telefone").value(clienteDto.getTelefone()))
                .andExpect(jsonPath("endereco").value(clienteDto.getEndereco()));
    }


    @Test
    @DisplayName("Devem obter um cliente pelo cnpj")
    public void criarClienteByCnpjTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();

        repository.save(cliente);
        when(repository.findBycnpj(clienteDto.getCnpj())).thenReturn(cliente);

        assertThat(cliente.getId()).isEqualTo(1L);
        assertThat(cliente.getNome()).isEqualTo("José");
        assertThat(cliente.getTelefone()).isEqualTo("(11) 98521-1715");
    }

    @Test
    @DisplayName("Devem obter um cliente pelo ID")
    public void pesquisaClientePorIDTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCompleto().criar();

        repository.save(cliente);
        when(repository.findById(clienteDto.getId())).thenReturn(Optional.of(cliente));

        assertThat(cliente.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve deletar um cliente pelo ID")
    public void deletarUmClientePeloID_sucesso() throws Exception {
        ClienteNotFoundException exception = new ClienteNotFoundException();
        clienteService.deleteById(1L);

//        Execução (When)
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ENDPOINT_URL.concat("/" + 1L));
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andDo((ResultHandler) exception);
    }
}