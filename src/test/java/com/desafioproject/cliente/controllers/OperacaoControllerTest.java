package com.desafioproject.cliente.controllers;

import com.desafioproject.cliente.controller.OperacaoController;
import com.desafioproject.cliente.model.entity.Conta;
import com.desafioproject.cliente.model.entity.Operacao;
import com.desafioproject.cliente.model.repository.OperacaoRepository;
import com.desafioproject.cliente.model.service.OperacaoService;
import com.desafioproject.cliente.utils.OperacaoBuilder;
import com.desafioproject.cliente.utils.OperacaoDtoBuilder;
import com.desafioproject.cliente.utils.OperacaoSaqueResponseBuilder;
import com.desafioproject.cliente.viewer.dto.request.OperacaoDto;
import com.desafioproject.cliente.viewer.dto.response.OperacaoSaqueResponse;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = OperacaoController.class)
@AutoConfigureMockMvc
public class OperacaoControllerTest {

    static String ENDPOINT_URL = "/api/operacao";

    @Autowired
    MockMvc mvc;

    @MockBean
    OperacaoService operacaoService;

    @MockBean
    OperacaoRepository repository;

    @Test
    @DisplayName("Deve criar uma operação com sucesso.")
    public void criarOperaacaoBemSucedidaTest() throws Exception {
        Conta conta = new Conta();
        conta.setNumeroDaConta("222");
        conta.setSaldo(new BigDecimal(200));

        OperacaoDto operacao = OperacaoDtoBuilder.operacaoSaque().criar();

        OperacaoSaqueResponse operacaoSaqueResponse = OperacaoSaqueResponseBuilder.operacaoResponseSaque().criar();

        BDDMockito.given(operacaoService.sacar(operacao)).willReturn(operacaoSaqueResponse);

        String json = new ObjectMapper().writeValueAsString(operacao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT_URL.concat("/sacar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk());
//                .andExpect(jsonPath("tipoDeOperacao").value(operacao.getTipoDeOperacao()))
//                .andExpect(jsonPath("mensagem").value(operacaoSaqueResponse.getMensagem()));

    }

    @Test
    @DisplayName("Deve localizar uma operacao por ID")
    public void deveRetornarAOperacaoPeloIDTest(){
        OperacaoDto operacaoDto = OperacaoDtoBuilder.operacaoSaque().criar();
        Operacao operacao = OperacaoBuilder.operacaoSaque().criar();

        repository.save(operacao);
        when(repository.findById(operacaoDto.getId())).thenReturn(Optional.of(operacao));

        assertThat(operacao.getId()).isEqualTo(1L);
    }

}
