package com.desafioProject.Cliente.model.repository;

import com.desafioProject.Cliente.model.entity.Conta;
import com.desafioProject.Cliente.utils.ContaBuilder;
import com.desafioProject.Cliente.utils.ContaDtoBuilder;
import com.desafioProject.Cliente.viewer.dto.request.ContaDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContaRepositoryTest {

    @MockBean
    TestEntityManager testEntityManager;

    @MockBean
    ContaRepository contaRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente na base com o numero da conta informado")
    public void retornaVerdadeiroSeContaForExistenteTest() {
        String numeroDaConta = "111";
        Conta conta = ContaBuilder.contaFisica().criar();
        testEntityManager.persist(conta);

        when(contaRepository.existsBynumeroDaConta(Mockito.anyString())).thenReturn(true);
        boolean exists = contaRepository.existsBynumeroDaConta(numeroDaConta);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve obter uma conta pelo número da conta")
    public void encontrarContaPeloNumeroDaConta() {
        ContaDto contaDto = ContaDtoBuilder.contaFisicaDto().criar();
        Conta conta = ContaBuilder.contaFisica().criar();

        testEntityManager.persist(conta);
        when(contaRepository.findBynumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(conta);

        assertThat(conta.getNumeroDaConta()).isNotNull();
        assertThat(contaDto.getNumeroDaConta()).isEqualTo(conta.getNumeroDaConta());
    }

    @Test
    @DisplayName("Deve criar uma conta")
    public void criarContaTest() {
        Conta conta = ContaBuilder.contaFisica().criar();

        when(contaRepository.save(conta)).thenReturn(conta);
        Conta contaSalva = contaRepository.save(conta);

        assertThat(contaSalva.getNumeroDaConta()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar uma conta e persistir se o retorno for nulo")
    public void deletarContaTest() {
        Conta conta = ContaBuilder.contaFisica().criar();

        contaRepository.save(conta);
        testEntityManager.persist(conta);

        Conta contaEncontrada = testEntityManager.find(Conta.class, conta.getId());
        contaRepository.delete(contaEncontrada);
        Conta contaDeletada = testEntityManager.find(Conta.class, conta.getId());

        assertThat(contaDeletada).isNull();
    }
}
