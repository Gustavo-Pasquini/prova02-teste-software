package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes escritos antes da implementação, um requisito por vez
 * (ciclo REQUISITO -> TESTE -> RED -> IMPLEMENTAÇÃO -> GREEN -> REFACTOR).
 */
class CarrinhoTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Requisito 1 - carrinho recém-criado deve ter total R$ 0,00")
    void carrinhoNovoDeveTerTotalZero() {
        Carrinho carrinho = new Carrinho();

        assertEquals(0.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 2 - adicionar um produto soma o preço ao total")
    void deveAdicionarUmProduto() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.0));

        assertEquals(100.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 3 - adicionar vários produtos soma os preços")
    void deveSomarOsPrecosDeVariosProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.0));
        carrinho.adicionar(new Produto("Mouse", 50.0));

        assertEquals(150.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 4 - produto adicionado deve poder ser removido")
    void deveRemoverUmProdutoDoCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.0));
        carrinho.adicionar(new Produto("Mouse", 50.0));

        boolean removido = carrinho.remover("Mouse");

        assertTrue(removido);
        assertEquals(100.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 4 - remover produto inexistente não altera o carrinho")
    void naoDeveRemoverProdutoInexistente() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.0));

        boolean removido = carrinho.remover("Monitor");

        assertFalse(removido);
        assertEquals(100.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 5 - produto com preço negativo deve ser recusado com exceção")
    void naoDeveAdicionarProdutoComPrecoNegativo() {
        Carrinho carrinho = new Carrinho();
        Produto invalido = new Produto("Teclado", -100.0);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> carrinho.adicionar(invalido)
        );

        assertEquals("Preço do produto não pode ser negativo", erro.getMessage());
        assertEquals(0.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 5 - fronteira: produto gratuito (preço zero) é aceito")
    void deveAdicionarProdutoComPrecoZero() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Brinde", 0.0));

        assertEquals(0.0, carrinho.getTotal(), DELTA);
        assertEquals(1, carrinho.getQuantidadeDeItens());
    }
}
