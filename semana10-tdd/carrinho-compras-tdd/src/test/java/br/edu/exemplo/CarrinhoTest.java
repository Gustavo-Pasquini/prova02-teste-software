package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
