package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes complementares escritos pela equipe para cobrir os desvios de
 * validação e as fronteiras que os testes fornecidos não exercitam,
 * atingindo cobertura total de {@link ContaBancaria}.
 */
class ContaBancariaCoberturaTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Conta nova deve iniciar com saldo zero")
    void contaNovaDeveIniciarComSaldoZero() {
        assertEquals(0, new ContaBancaria().getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Depósito de valor zero não altera o saldo")
    void naoDeveDepositarValorZero() {
        ContaBancaria conta = new ContaBancaria();

        conta.depositar(0);

        assertEquals(0, conta.getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Depósito de valor negativo não altera o saldo")
    void naoDeveDepositarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);

        conta.depositar(-50);

        assertEquals(100, conta.getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Saque de valor zero não altera o saldo")
    void naoDeveSacarValorZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);

        conta.sacar(0);

        assertEquals(100, conta.getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Saque de valor negativo não altera o saldo")
    void naoDeveSacarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);

        conta.sacar(-30);

        assertEquals(100, conta.getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Fronteira: saque igual ao saldo deve zerar a conta")
    void deveSacarValorIgualAoSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);

        conta.sacar(100);

        assertEquals(0, conta.getSaldo(), DELTA);
    }

    @Test
    @DisplayName("Fronteira: saque de um centavo acima do saldo é recusado")
    void naoDeveSacarUmCentavoAcimaDoSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);

        conta.sacar(100.01);

        assertEquals(100, conta.getSaldo(), DELTA);
    }
}
