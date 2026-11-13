package br.edu.exemplo;

/**
 * Ordenação de um vetor de inteiros pelo algoritmo Bubble Sort.
 *
 * <p>Implementação construída por TDD, guiada pelos requisitos de
 * {@code REQUISITOS.md}:</p>
 * <ol>
 *   <li>ordenar um vetor desordenado em ordem crescente;</li>
 *   <li>manter inalterado um vetor já ordenado;</li>
 *   <li>ordenar vetores com valores repetidos, preservando as ocorrências.</li>
 * </ol>
 */
public class BubbleSort {

    private final int[] numeros;

    public BubbleSort(int[] numeros) {
        this.numeros = numeros;
    }

    /**
     * Ordena o vetor em ordem crescente, no próprio vetor (in-place).
     *
     * <p>Refatoração do ciclo GREEN: a varredura para assim que uma passagem
     * completa não realiza nenhuma troca, o que evita percorrer novamente um
     * vetor já ordenado (requisito 2).</p>
     */
    public void ordenar() {
        for (int fim = numeros.length - 1; fim > 0; fim--) {
            boolean houveTroca = false;

            for (int i = 0; i < fim; i++) {
                if (numeros[i] > numeros[i + 1]) {
                    trocar(i, i + 1);
                    houveTroca = true;
                }
            }

            if (!houveTroca) {
                return;
            }
        }
    }

    public int[] getNumeros() {
        return numeros;
    }

    private void trocar(int i, int j) {
        int auxiliar = numeros[i];
        numeros[i] = numeros[j];
        numeros[j] = auxiliar;
    }
}
