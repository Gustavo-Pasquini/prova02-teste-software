package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private final List<Produto> produtos = new ArrayList<>();

    public void adicionar(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo");
        }
        produtos.add(produto);
    }

    public boolean remover(String nomeDoProduto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(nomeDoProduto)) {
                produtos.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getQuantidadeDeItens() {
        return produtos.size();
    }

    public double getTotal() {
        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }
}
