package com.mycompany.agenciabancaria.model;

public class ContaCorrente extends Conta {

    /**
     * Construtor para criar uma nova conta corrente.
     * 
     * @param numero       O número da conta.
     * @param saldoInicial O saldo inicial da conta corrente.
     * @param dono         O titular da conta.
     */
    public ContaCorrente(int numero, double saldoInicial, Titular dono) {
        super(numero, saldoInicial, dono); // Chama o construtor da classe abstrata Conta
    }

    /**
     * Método para retornar o tipo da conta. 
     * No caso da ContaCorrente, sempre retorna "Corrente".
     * 
     * @return O tipo da conta (Conta Corrente).
     */
    @Override
    public String getTipo() {
        return "Corrente"; // Tipo da conta
    }
}
