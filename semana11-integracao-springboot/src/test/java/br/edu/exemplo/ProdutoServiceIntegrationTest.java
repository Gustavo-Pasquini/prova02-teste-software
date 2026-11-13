package br.edu.exemplo;

import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercício 1 — integração entre Service, Repository e banco H2.
 *
 * <p>Nenhuma dependência é substituída por mock: o contexto real do Spring é
 * carregado com {@code @SpringBootTest} e o {@code ProdutoService} conversa com
 * o {@code ProdutoRepository} e com o banco H2 em memória.</p>
 *
 * <p>O {@link EntityManager} é usado apenas para forçar {@code flush} e
 * {@code clear}: assim a consulta posterior com {@code findById} precisa ir ao
 * banco, e não devolver a instância que ainda estava no contexto de
 * persistência — o que tornaria a verificação mais fraca.</p>
 */
@SpringBootTest
@Transactional
class ProdutoServiceIntegrationTest {

    @Autowired
    ProdutoService service;

    @Autowired
    ProdutoRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("CT01 - cadastro válido: o produto recebe ID e é encontrado no repositório")
    void deveCadastrarERecuperarProduto() {
        Produto cadastrado = service.cadastrar("Teclado", 150.0);

        assertNotNull(cadastrado, "o serviço deve devolver o produto cadastrado");
        assertNotNull(cadastrado.getId(), "o produto deve receber um ID gerado pelo banco");

        sincronizarComOBanco();

        Optional<Produto> encontrado = repository.findById(cadastrado.getId());

        assertTrue(encontrado.isPresent(), "o produto deve estar persistido no H2");
        assertEquals("Teclado", encontrado.get().getNome());
        assertEquals(150.0, encontrado.get().getPreco(), 0.001);
    }

    @Test
    @DisplayName("CT02 - nome em branco: lança IllegalArgumentException e nada é persistido")
    void naoDevePersistirProdutoComNomeEmBranco() {
        long registrosAntes = repository.count();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar("   ", 80.0)
        );

        assertEquals("Nome obrigatório", erro.getMessage());

        sincronizarComOBanco();

        assertEquals(registrosAntes, repository.count(),
                "a contagem de registros não deve aumentar depois de uma tentativa inválida");
    }

    @Test
    @DisplayName("CT03 (desafio) - atualização de preço fica persistida no banco")
    void deveAtualizarPrecoPersistido() {
        Produto mouse = service.cadastrar("Mouse", 80.0);
        Long id = mouse.getId();

        service.atualizarPreco(id, 95.0);

        sincronizarComOBanco();

        Produto recarregado = repository.findById(id).orElseThrow();

        assertEquals(95.0, recarregado.getPreco(), 0.001,
                "a consulta posterior ao repositório deve retornar o preço atualizado");
        assertEquals("Mouse", recarregado.getNome(), "o nome não deve ser alterado");
    }

    @Test
    @DisplayName("CT06 (complementar) - preço não positivo é recusado e nada é persistido")
    void naoDevePersistirProdutoComPrecoNaoPositivo() {
        long registrosAntes = repository.count();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar("Monitor", 0.0)
        );

        assertEquals("Preço deve ser positivo", erro.getMessage());

        sincronizarComOBanco();

        assertEquals(registrosAntes, repository.count());
    }

    @Test
    @DisplayName("CT07 (complementar) - buscar ID inexistente lança IllegalArgumentException")
    void deveFalharAoBuscarProdutoInexistente() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.buscar(999_999L)
        );

        assertEquals("Produto não encontrado", erro.getMessage());
    }


    @Test
    @DisplayName("CT09 (complementar) - nome nulo é recusado e nada é persistido")
    void naoDevePersistirProdutoComNomeNulo() {
        long registrosAntes = repository.count();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(null, 80.0)
        );

        assertEquals("Nome obrigatório", erro.getMessage());

        sincronizarComOBanco();

        assertEquals(registrosAntes, repository.count());
    }

    @Test
    @DisplayName("CT10 (complementar) - atualização com preço inválido não altera o registro")
    void naoDeveAtualizarPrecoComValorInvalido() {
        Produto monitor = service.cadastrar("Monitor", 700.0);
        Long id = monitor.getId();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarPreco(id, -1.0)
        );

        assertEquals("Preço deve ser positivo", erro.getMessage());

        sincronizarComOBanco();

        assertEquals(700.0, repository.findById(id).orElseThrow().getPreco(), 0.001);
    }

    /**
     * Envia ao banco o que está pendente na transação e limpa o contexto de
     * persistência, para que a próxima consulta leia de fato do H2.
     */
    private void sincronizarComOBanco() {
        entityManager.flush();
        entityManager.clear();
    }
}
