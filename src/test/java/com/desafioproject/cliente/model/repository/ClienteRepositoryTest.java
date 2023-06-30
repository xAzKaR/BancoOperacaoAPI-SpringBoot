package com.desafioproject.cliente.model.repository;

import com.desafioproject.cliente.model.entity.Cliente;
import com.desafioproject.cliente.utils.ClienteBuilder;
import com.desafioproject.cliente.utils.ClienteDtoBuilder;
import com.desafioproject.cliente.viewer.dto.request.ClienteDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClienteRepositoryTest {

    @MockBean
    TestEntityManager testEntityManager;

    @MockBean
    ClienteRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente na base com o cnpj informado")
    void retornaVerdadeiroSeClienteExistirTest() {
        String cnpj = "70.794.651/0001-86";
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();
        testEntityManager.persist(cliente);

        when(repository.existsBycnpj(Mockito.anyString())).thenReturn(true);
        boolean exists = repository.existsBycnpj(cnpj);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve obter um cliente por cpf")
    void encontrarClienteByCpfTest() {
        ClienteDto clienteDto = ClienteDtoBuilder.criarClienteDto().criar();
        Cliente cliente = ClienteBuilder.clienteCpf().criar();

        testEntityManager.persist(cliente);
        when(repository.findBycpf(clienteDto.getCpf())).thenReturn(cliente);

        assertThat(cliente.getCpf()).isNotNull();
        assertThat(clienteDto.getCpf()).isEqualTo(cliente.getCpf());
    }

    @Test
    @DisplayName("Devem obter um cliente pelo cnpj")
    void encontrarClienteByCnpjTest() {
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
    @DisplayName("Deve retornar verdadeiro quando não existir um cliente na base com o cnpj informado")
    void retornaFalsoSeClienteNaoExistirTest() {
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();
        testEntityManager.persist(cliente);

        boolean exists = repository.existsBycnpj(cliente.getCnpj());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    void criarClienteTest() {
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();

        when(repository.save(cliente)).thenReturn(cliente);
        Cliente clienteSalvo = repository.save(cliente);

        assertThat(clienteSalvo.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um cliente e persistir se o mesmo foi deletado")
    void deleteClienteEPersistirSeFoiDeletadoTest() {
        Cliente cliente = ClienteBuilder.clienteCnpj().criar();

        repository.save(cliente);
        testEntityManager.persist(cliente);

        Cliente clienteEncontrado = testEntityManager.find(Cliente.class, cliente.getId());
        repository.delete(clienteEncontrado);
        Cliente clienteDeletado = testEntityManager.find(Cliente.class, cliente.getId());

        assertThat(clienteDeletado).isNull();
    }
}
