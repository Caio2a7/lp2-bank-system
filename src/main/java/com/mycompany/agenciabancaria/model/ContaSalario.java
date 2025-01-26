package com.mycompany.agenciabancaria.model;

public class ContaSalario extends Conta {
    private static final int LIMITE_SAQUES = 3;
    private int saquesRealizados = 0;

    public ContaSalario(int numero, double saldoInicial, Titular dono) {
        super(numero, saldoInicial, dono);
    }

    @Override
    public String getTipo() {
        return "Salário";
    }

    @Override
    public void Depositar(double valor) {
        // Somente depósitos autorizados pelo empregador
        // Aqui você pode adicionar uma lógica para verificar a origem do depósito
        super.Depositar(valor);
    }

    @Override
    public boolean Sacar(double valor) {
        if (saquesRealizados < LIMITE_SAQUES) {
            if (super.Sacar(valor)) {
                saquesRealizados++;
                return true;
            }
        }
        throw new RuntimeException("Limite de saques excedido.");
    }
}
