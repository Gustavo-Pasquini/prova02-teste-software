package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes complementares escritos pela equipe para completar a cobertura de
 * {@link Produto}: nome do produto, entradas/retiradas iguais a zero e
 * fronteiras da retirada.
 */
class ProdutoCoberturaTest {

    @Test
    @DisplayName("O produto deve guardar o nome informado na criação")
    void deveGuardarONomeDoProduto() {
        assertEquals("Teclado", new Produto("Teclado").getNome());
    }

    @Test
    @DisplayName("Entrada de quantidade zero não altera o estoque")
    void naoDeveAdicionarQuantidadeZero() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(0);

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    @DisplayName("Retirada de quantidade zero não altera o estoque")
    void naoDeveRetirarQuantidadeZero() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        produto.retirarEstoque(0);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    @DisplayName("Retirada de quantidade negativa não altera o estoque")
    void naoDeveRetirarQuantidadeNegativa() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        produto.retirarEstoque(-5);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    @DisplayName("Fronteira: retirar exatamente todo o estoque é permitido")
    void deveRetirarTodoOEstoqueDisponivel() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        produto.retirarEstoque(10);

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    @DisplayName("Fronteira: retirar uma unidade acima do estoque é recusado")
    void naoDeveRetirarUmaUnidadeAcimaDoEstoque() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        produto.retirarEstoque(11);

        assertEquals(10, produto.getQuantidade());
    }
}
