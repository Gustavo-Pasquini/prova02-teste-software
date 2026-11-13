package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    @DisplayName("Requisito 6 - compras de R$ 500,00 ou mais recebem 10% de desconto")
    void deveAplicarDescontoDeDezPorCento() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 600.0));

        assertEquals(600.0, carrinho.getSubtotal(), DELTA);
        assertEquals(60.0, carrinho.getDesconto(), DELTA);
        assertEquals(540.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 - fronteira inferior: R$ 499,99 não recebe desconto")
    void naoDeveAplicarDescontoAbaixoDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 499.99));

        assertEquals(0.0, carrinho.getDesconto(), DELTA);
        assertEquals(499.99, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 - fronteira exata: R$ 500,00 já recebe desconto")
    void deveAplicarDescontoNoValorExatoDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 500.00));

        assertEquals(50.0, carrinho.getDesconto(), DELTA);
        assertEquals(450.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 - fronteira superior: R$ 500,01 recebe desconto")
    void deveAplicarDescontoAcimaDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 500.01));

        assertEquals(450.009, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 - desconto considera a soma de vários produtos")
    void deveAplicarDescontoSobreASomaDosProdutos() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 300.0));
        carrinho.adicionar(new Produto("Monitor", 250.0));

        assertEquals(550.0, carrinho.getSubtotal(), DELTA);
        assertEquals(495.0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Refactor - a lista de produtos exposta não pode ser alterada por fora")
    void naoDevePermitirAlterarAListaDeProdutosPorFora() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.0));

        List<Produto> produtos = carrinho.getProdutos();

        assertEquals(1, produtos.size());
        assertEquals("Teclado", produtos.get(0).getNome());
        assertThrows(
                UnsupportedOperationException.class,
                () -> produtos.add(new Produto("Mouse", 50.0))
        );
    }
}
