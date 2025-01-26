package com.mycompany.agenciabancaria.model;

public class ContaCorrente extends Conta {
    private static final double TAXA_MANUTENCAO = 15.0;

    public ContaCorrente(int numero, double saldoInicial, Titular dono) {
        super(numero, saldoInicial, dono);
    }

    @Override
    public String getTipo() {
        return "Corrente";
    }

    public void aplicarTaxaManutencao() {
        if (saldo >= TAXA_MANUTENCAO) {
            saldo -= TAXA_MANUTENCAO;
        } else {
            throw new RuntimeException("Saldo insuficiente para aplicar a taxa de manutenção.");
        }
    }

    @Override
    public boolean Sacar(double valor) {
        aplicarTaxaManutencao();
        return super.Sacar(valor);
    }

    @Override
    public boolean transferir(double valor, Conta ContaDestino) {
        aplicarTaxaManutencao();
        return super.transferir(valor, ContaDestino);
    }
}
