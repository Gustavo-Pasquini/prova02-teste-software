package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testes complementares escritos pela equipe para fechar a cobertura total
 * (instruções, linhas, desvios e métodos) da classe {@link BubbleSort}.
 *
 * <p>Os testes fornecidos em {@code BubbleSortTest} não foram alterados,
 * conforme a restrição da atividade.</p>
 */
class BubbleSortCoberturaTest {

    @Test
    @DisplayName("Vetor vazio não deve causar erro")
    void deveSuportarVetorVazio() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("Vetor com um único elemento permanece igual")
    void deveSuportarVetorComUmElemento() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{7});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{7}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("Vetor em ordem decrescente é totalmente invertido")
    void deveOrdenarVetorEmOrdemDecrescente() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{9, 7, 5, 3, 1});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("Números negativos são ordenados corretamente")
    void deveOrdenarNumerosNegativos() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{0, -5, 3, -1});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{-5, -1, 0, 3}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("A ordenação ocorre no próprio vetor recebido (in-place)")
    void deveOrdenarNoProprioVetorRecebido() {
        int[] vetor = {2, 1};
        BubbleSort bubbleSort = new BubbleSort(vetor);

        bubbleSort.ordenar();

        assertSame(vetor, bubbleSort.getNumeros());
        assertArrayEquals(new int[]{1, 2}, vetor);
    }
}
