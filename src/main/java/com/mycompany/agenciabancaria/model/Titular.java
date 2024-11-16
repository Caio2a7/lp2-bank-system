package com.mycompany.agenciabancaria.model;

import java.util.ArrayList;
import java.util.List;

public class Titular {

    /**
     * Nome do titular da conta.
     */
    private String nome;
    
    /**
     * CPF do titular da conta.
     */
    private String cpf;
    
    /**
     * Senha de acesso do titular.
     */
    private String senha;
    
    /**
     * Lista de contas do titular.
     */
    private List<Conta> contas;

    /**
     * Construtor para criar um titular de conta.
     * 
     * @param nome  O nome do titular.
     * @param cpf   O CPF do titular.
     * @param senha A senha do titular.
     */
    public Titular(String nome, String cpf, String senha) {
        this.nome = nome; 
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>(); // Inicializa a lista de contas
    }

    /**
     * Retorna o nome do titular.
     * 
     * @return O nome do titular.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o CPF do titular.
     * 
     * @return O CPF do titular.
     */
    public String getCPF() {
        return cpf;
    }

    /**
     * Retorna a senha do titular.
     * 
     * @return A senha do titular.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Retorna a lista de contas do titular.
     * 
     * @return A lista de contas associadas ao titular.
     */
    public List<Conta> getContas() {
        return contas;
    }

    /**
     * Adiciona uma conta à lista de contas do titular.
     * 
     * @param conta A conta a ser adicionada.
     */
    public void AdicionarConta(Conta conta) {
        contas.add(conta); // Adiciona a conta à lista
    }
}
