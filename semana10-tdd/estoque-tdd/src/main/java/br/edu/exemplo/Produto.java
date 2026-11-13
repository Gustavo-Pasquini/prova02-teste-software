package br.edu.exemplo;

/**
 * Produto com controle de quantidade em estoque.
 *
 * <p>Implementação construída por TDD a partir de
 * {@code requisitos_estoque_tdd.md}:</p>
 * <ol>
 *   <li>o produto inicia com estoque zero;</li>
 *   <li>é possível adicionar unidades;</li>
 *   <li>entradas sucessivas são acumuladas;</li>
 *   <li>é possível retirar unidades disponíveis;</li>
 *   <li>retirada maior que o estoque é recusada;</li>
 *   <li>entrada com quantidade negativa ou zero é recusada.</li>
 * </ol>
 *
 * <p>Conforme os requisitos, operações inválidas não alteram o estoque e não
 * lançam exceção.</p>
 */
public class Produto {

    private final String nome;
    private int quantidade;

    public Produto(String nome) {
        this.nome = nome;
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /** Adiciona unidades ao estoque. Quantidades menores ou iguais a zero são ignoradas. */
    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            return;
        }
        this.quantidade += quantidade;
    }

    /**
     * Retira unidades do estoque. Quantidades menores ou iguais a zero e
     * quantidades maiores que o estoque disponível são ignoradas.
     */
    public void retirarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > this.quantidade) {
            return;
        }
        this.quantidade -= quantidade;
    }
}
