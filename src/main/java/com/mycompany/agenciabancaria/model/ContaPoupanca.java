package com.mycompany.agenciabancaria.model;

public class ContaPoupanca extends Conta {
    private static final double TAXA_RENDIMENTO = 0.005; // 0.5% de rendimento mensal

    public ContaPoupanca(int numero, double saldoInicial, Titular dono) {
        super(numero, saldoInicial, dono);
    }

    @Override
    public String getTipo() {
        return "Poupança";
    }

    public void aplicarRendimentoMensal() {
        saldo += saldo * TAXA_RENDIMENTO;
    }
}
