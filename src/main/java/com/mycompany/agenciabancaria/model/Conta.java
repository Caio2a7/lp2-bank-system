package com.mycompany.agenciabancaria.model;

public abstract class Conta {
    protected int numero; // Número da conta bancária
    protected double saldo; // Saldo disponível na conta
    protected Titular dono; // Titular da conta

    /**
     * Construtor para inicializar uma nova conta.
     *
     * @param numero      O número da conta.
     * @param saldoInicial O saldo inicial da conta.
     * @param dono        O titular da conta.
     */
    public Conta(int numero, double saldoInicial, Titular dono) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.dono = dono;
    }

    /**
     * Método para retornar o número da conta.
     * 
     * @return O número da conta.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Método para retornar o saldo atual da conta.
     * 
     * @return O saldo atual da conta.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Método para realizar um depósito na conta.
     * 
     * @param valor O valor a ser depositado na conta.
     */
    public void Depositar(double valor) {
        saldo = saldo + valor;
    }

    /**
     * Método para realizar um saque da conta.
     * 
     * @param valor O valor a ser sacado da conta.
     * @return Retorna true se o saque foi bem-sucedido, e false caso contrário.
     */
    public boolean Sacar(double valor) {
        if (saldo >= valor) {
            saldo = saldo - valor;
            return true; // Saque realizado com sucesso
        }
        return false; // Saldo insuficiente
    }

    /**
     * Método para transferir um valor de uma conta para outra.
     * 
     * @param valor          O valor a ser transferido.
     * @param ContaDestino   A conta de destino para onde o valor será transferido.
     * @return Retorna true se a transferência foi bem-sucedida, e false caso contrário.
     */
    public boolean transferir(double valor, Conta ContaDestino) {
        if (saldo >= valor) {
            saldo = saldo - valor;
            ContaDestino.Depositar(valor); // Realizando o depósito na conta de destino
            return true; // Transferência realizada com sucesso
        }
        return false; // Saldo insuficiente
    }

    /**
     * Método abstrato para retornar o tipo da conta (por exemplo, Conta Corrente ou Conta Poupança).
     * 
     * @return O tipo da conta.
     */
    public abstract String getTipo();
}
