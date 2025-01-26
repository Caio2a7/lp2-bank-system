package com.mycompany.agenciabancaria.model;

import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private String nome;
    private List<Conta> contas;

    public Agencia(String nome) {
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public Conta buscarContaPorNumero(int numero) {
        return contas.stream()
                .filter(conta -> conta.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }
}
