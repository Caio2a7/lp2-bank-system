package com.mycompany.agenciabancaria.model;

public class ContaPoupanca extends Conta {

    /**
     * Construtor para criar uma nova conta poupança.
     * 
     * @param numero       O número da conta.
     * @param saldoInicial O saldo inicial da conta poupança.
     * @param dono         O titular da conta.
     */
    public ContaPoupanca(int numero, double saldoInicial, Titular dono) {
        super(numero, saldoInicial, dono); // Chama o construtor da classe abstrata Conta
    }

    /**
     * Método para retornar o tipo da conta. 
     * No caso da ContaPoupanca, sempre retorna "Poupança".
     * 
     * @return O tipo da conta (Conta Poupança).
     */
    @Override
    public String getTipo() {
        return "Poupança"; // Tipo da conta
    }
}
