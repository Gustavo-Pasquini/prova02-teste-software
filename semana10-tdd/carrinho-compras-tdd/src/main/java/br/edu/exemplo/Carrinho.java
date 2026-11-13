package br.edu.exemplo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Carrinho de compras construído por TDD a partir de {@code REQUISITOS.md}.
 *
 * <ol>
 *   <li>carrinho novo tem total R$ 0,00;</li>
 *   <li>adicionar um produto soma seu preço;</li>
 *   <li>vários produtos somam seus preços;</li>
 *   <li>um produto pode ser removido pelo nome;</li>
 *   <li>produto com preço negativo é recusado com {@link IllegalArgumentException};</li>
 *   <li>subtotal a partir de R$ 500,00 recebe 10% de desconto.</li>
 * </ol>
 */
public class Carrinho {

    /** Valor mínimo do subtotal para que o desconto seja concedido. */
    private static final double SUBTOTAL_MINIMO_PARA_DESCONTO = 500.0;

    /** Percentual de desconto aplicado ao subtotal elegível. */
    private static final double PERCENTUAL_DE_DESCONTO = 0.10;

    private final List<Produto> produtos = new ArrayList<>();

    /**
     * Adiciona um produto ao carrinho.
     *
     * @throws IllegalArgumentException se o preço do produto for negativo
     */
    public void adicionar(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo");
        }
        produtos.add(produto);
    }

    /**
     * Remove do carrinho a primeira ocorrência do produto com o nome informado.
     *
     * @return {@code true} se algum produto foi removido
     */
    public boolean remover(String nomeDoProduto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(nomeDoProduto)) {
                produtos.remove(i);
                return true;
            }
        }
        return false;
    }

    /** Produtos do carrinho, em uma lista somente leitura. */
    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public int getQuantidadeDeItens() {
        return produtos.size();
    }

    /** Soma dos preços dos produtos, sem desconto. */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (Produto produto : produtos) {
            subtotal += produto.getPreco();
        }
        return subtotal;
    }

    /** Desconto concedido: 10% do subtotal quando ele atinge R$ 500,00. */
    public double getDesconto() {
        double subtotal = getSubtotal();
        if (subtotal < SUBTOTAL_MINIMO_PARA_DESCONTO) {
            return 0.0;
        }
        return subtotal * PERCENTUAL_DE_DESCONTO;
    }

    /** Valor a pagar: subtotal menos o desconto. */
    public double getTotal() {
        return getSubtotal() - getDesconto();
    }
}
