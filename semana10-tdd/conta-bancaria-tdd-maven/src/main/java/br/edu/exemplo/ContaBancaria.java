package br.edu.exemplo;

/**
 * Conta bancária com saldo, depósito e saque.
 *
 * <p>Implementação construída por TDD a partir dos testes fornecidos:</p>
 * <ol>
 *   <li>depositar um valor aumenta o saldo;</li>
 *   <li>depósitos sucessivos são acumulados;</li>
 *   <li>sacar reduz o saldo;</li>
 *   <li>saque maior que o saldo é recusado e não altera o saldo.</li>
 * </ol>
 *
 * <p>Operações inválidas são ignoradas (não lançam exceção), decisão derivada
 * do teste {@code naoDevePermitirSaqueMaiorQueSaldo}, que verifica apenas que
 * o saldo permanece inalterado.</p>
 */
public class ContaBancaria {

    private double saldo;

    public ContaBancaria() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    /** Deposita o valor informado. Valores não positivos são ignorados. */
    public void depositar(double valor) {
        if (valor <= 0) {
            return;
        }
        saldo += valor;
    }

    /**
     * Saca o valor informado. Valores não positivos e valores maiores que o
     * saldo disponível são ignorados, mantendo o saldo inalterado.
     */
    public void sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            return;
        }
        saldo -= valor;
    }
}
