package com.desafioProject.Cliente.controllers;


import com.desafioProject.Cliente.controller.ContaController;
import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.model.entity.enums.TipoDeConta;
import com.desafioProject.Cliente.model.repository.ContaRepository;
import com.desafioProject.Cliente.model.service.ContaService;
import com.desafioProject.Cliente.utils.ContaBuilder;
import com.desafioProject.Cliente.utils.ContaDtoBuilder;
import com.desafioProject.Cliente.utils.ContaResponseBuilder;
import com.desafioProject.Cliente.viewer.dto.request.ContaDto;
import com.desafioProject.Cliente.viewer.dto.response.ContaResponse;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ContaController.class)
@AutoConfigureMockMvc
public class ContaControllerTest {

    static String ENDPOINT_URL = "/api/conta";

    @Autowired
    MockMvc mvc;

    @MockBean
    ContaService contaService;

    @MockBean
    ContaRepository repository;

    @Test
    @DisplayName("Deve criar uma conta com sucesso.")
    public void criarContaRetornaResponseERetornaStatusCreatedTest() throws Exception {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        ContaResponse contaResponse = ContaResponseBuilder.responseContaFisica().criar();

        BDDMockito.given(contaService.salvar(contaDto)).willReturn(contaResponse);

        String json = new ObjectMapper().writeValueAsString(contaDto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("numeroDaConta").value(contaResponse.getNumeroDaConta()))
                .andExpect(jsonPath("agencia").value(contaResponse.getAgencia()))
                .andExpect(jsonPath("tipo").value(String.valueOf(contaResponse.getTipo())))
                .andExpect(jsonPath("digitoVerificador").value(contaResponse.getDigitoVerificador()))
                .andExpect(jsonPath("mensagem").value(contaResponse.getMensagem()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados insuficientes para criação de uma conta")
    public void criarContaInvalidaTest() throws Exception {
        ContaDto contaDto = ContaDtoBuilder.contaSemDadosDto().criar();
        String json = new ObjectMapper().writeValueAsString(contaDto);

        MockHttpServletRequestBuilder request;
        request = MockMvcRequestBuilders
                .post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andReturn().getResolvedException();
    }

    @Test
    @DisplayName("Deve obter informações da conta")
    public void recebeUmaListaDeContaPeloIDTest() throws Exception {
// Cenário (Given)
        ContaDto contaDto = ContaDtoBuilder.contaJuridicaDto().criar();

        BDDMockito.given(contaService.localizarId(contaDto.getId())).willReturn(contaDto);
//        Execução (When)
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ENDPOINT_URL.concat("/" + contaDto.getId()))
                .accept(MediaType.APPLICATION_JSON);
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(contaDto.getId()))
                .andExpect(jsonPath("numeroDaConta").value(contaDto.getNumeroDaConta()))
                .andExpect(jsonPath("agencia").value(contaDto.getAgencia()))
                .andExpect(jsonPath("tipo").value(String.valueOf(contaDto.getTipo())))
                .andExpect(jsonPath("digitoVerificador").value(contaDto.getDigitoVerificador()));
    }

    @Test
    @DisplayName("Devem obter uma conta pelo numero da conta")
    public void criarClienteByCnpjTest() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();

        repository.save(conta);
        when(repository.findBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(conta);

        assertThat(conta.getId()).isEqualTo(1L);
        assertThat(conta.getNumeroDaConta()).isEqualTo("111");
        assertThat(conta.getAgencia()).isEqualTo("7");
        assertThat(conta.getTipo()).isEqualTo(TipoDeConta.Fisica);
    }

    @Test
    @DisplayName("Devem obter uma conta pelo ID")
    public void pesquisaContaPorIDTest() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();

        repository.save(conta);
        when(repository.findById(contaDto.getId())).thenReturn(Optional.of(conta));

        assertThat(conta.getId()).isEqualTo(1L);
    }
}
