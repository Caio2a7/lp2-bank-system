package com.mycompany.agenciabancaria.controller;

import com.mycompany.agenciabancaria.model.*;
import com.mycompany.agenciabancaria.model.ArquivoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class AgenciaController {
    private List<Titular> usuarios = new ArrayList<>();
    private List<Agencia> agencias = new ArrayList<>();
    private Titular usuarioLogado;

    // Caminho do arquivo CSV
    private static final String CAMINHO_ARQUIVO_AGENCIAS = "data/agencias.csv";

    /**
     * Método para cadastrar um novo usuário na agência bancária.
     */
    
    public boolean cadastrarUsuario(String nome, String cpf, String senha) {
        for (Titular titular : usuarios) {
            if (titular.getCPF().equals(cpf)) {
                return false; // CPF já cadastrado
            }
        }
        Titular novoUsuario = new Titular(nome, cpf, senha);
        usuarios.add(novoUsuario);
        
        // Salvar o novo usuário no arquivo CSV
        try {
            ArquivoUtil.salvarUsuarioEmArquivo(novoUsuario, "data/usuarios.csv");
            System.out.println("Usuário cadastrado com sucesso e salvo no arquivo.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário no arquivo: " + e.getMessage());
        }
        return true; // Usuário cadastrado com sucesso
    }

    /**
     * Método para autenticar um usuário na agência bancária.
     */
    
    
     public Titular autenticarUsuario(String cpf, String senha, List<Titular> usuarios) {
        for (Titular usuario : usuarios) {
            System.out.println("Verificando usuário: " + usuario.getCPF());
            if (usuario.getCPF().equals(cpf) && usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                return usuarioLogado; // Usuário autenticado
            }
        }
        return null; // Credenciais inválidas
    }
    
    public void login() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=============== Login ==============");
        
        System.out.print("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Informe sua senha: ");
        String senha = scanner.nextLine();
        
        try {
            // Carrega os usuários do arquivo
            List<Titular> usuarios = ArquivoUtil.carregarUsuarios("data/usuarios.csv");
            
            // Tenta autenticar o usuário
            Titular usuarioAutenticado = autenticarUsuario(cpf, senha, usuarios);
            
            if (usuarioAutenticado != null) {
                System.out.println("Login realizado com sucesso!");
                // Aqui você pode redirecionar para a próxima etapa ou fluxo do sistema
            } else {
                System.out.println("Credenciais inválidas, verifique o CPF ou senha.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
    
    /**
     * Método para obter o titular que está atualmente logado.
     */
    public Titular getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Método para obter o saldo de uma conta específica do usuário logado.
     */
    public double getSaldo(int numeroConta) {
        for (Conta conta : usuarioLogado.getContas()) {
            if (conta.getNumero() == numeroConta) {
                return conta.getSaldo(); // Retorna o saldo da conta
            }
        }
        return -1; // Conta não encontrada
    }

    /**
     * Método para realizar um depósito em uma conta específica do usuário logado.
     */
    public boolean depositar(double valor, int numeroConta) {
        for (Conta conta : usuarioLogado.getContas()) {
            if (conta.getNumero() == numeroConta) {
                conta.Depositar(valor);
                return true; // Depósito realizado com sucesso
            }
        }
        return false; // Conta não encontrada ou erro no depósito
    }

    /**
     * Método para realizar um saque em uma conta específica do usuário logado.
     */
    public boolean sacar(double valor, int numeroConta) {
        for (Conta conta : usuarioLogado.getContas()) {
            if (conta.getNumero() == numeroConta) {
                System.out.println("Conta encontrada: " + conta.getNumero());
                if (valor <= conta.getSaldo()) {
                    conta.Sacar(valor);
                    System.out.println("Saque realizado com sucesso. Saldo restante: " + conta.getSaldo());
                    return true;
                } else {
                    System.out.println("Saldo insuficiente. Saldo atual: " + conta.getSaldo());
                    return false; // Saldo insuficiente
                }
            }
        }
        System.out.println("Conta não encontrada: " + numeroConta);
        return false; // Conta não encontrada
    }
    

    /**
     * Método para criar uma nova conta para o usuário logado.
     */
    public boolean criarNovaConta(int numero, double saldoInicial, int tipo) {
        if (usuarioLogado != null) {
            Conta novaConta = null;
            boolean existe = false;
    
            // Verificando se a conta já existe
            for (Conta conta : usuarioLogado.getContas()) {
                if (conta.getNumero() == numero) {
                    existe = true;
                    break;
                }
            }
    
            // Criando nova conta
            if (tipo == 1 && !existe) {
                novaConta = new ContaCorrente(numero, saldoInicial, usuarioLogado);
            } else if (tipo == 2 && !existe) {
                novaConta = new ContaPoupanca(numero, saldoInicial, usuarioLogado);
            }
    
            if (novaConta != null) {
                usuarioLogado.AdicionarConta(novaConta);
    
                // Agora, chamando o método para salvar as contas no arquivo CSV
                try {
                    ArquivoUtil.salvarContasEmArquivo(usuarioLogado, "data/contas.csv");
                } catch (IOException e) {
                    System.err.println("Erro ao salvar conta no arquivo: " + e.getMessage());
                }
                return true; // Conta criada com sucesso
            }
        }
        return false; // Falha na criação da conta
    }
    

    /**
     * Método para salvar agências em um arquivo CSV utilizando a função da classe ArquivoUtil.
     */
    
    public void salvarAgenciasEmArquivo() {
        try {
            // Usando a função salvarAgencias da classe ArquivoUtil para salvar as agências no arquivo CSV
            ArquivoUtil.salvarAgencias(agencias, CAMINHO_ARQUIVO_AGENCIAS);
            System.out.println("Agências salvas com sucesso no arquivo: " + CAMINHO_ARQUIVO_AGENCIAS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar agências no arquivo: " + e.getMessage());
        }
    }

    /**
     * Método para carregar agências de um arquivo CSV utilizando a função da classe ArquivoUtil.
     */
    public void carregarAgenciasDeArquivo() {
        try {
            // Usando a função carregarAgencias da classe ArquivoUtil para carregar as agências do arquivo CSV
            this.agencias = ArquivoUtil.carregarAgencias(CAMINHO_ARQUIVO_AGENCIAS);
            System.out.println("Agências carregadas com sucesso do arquivo: " + CAMINHO_ARQUIVO_AGENCIAS);
        } catch (IOException e) {
            System.err.println("Erro ao carregar agências do arquivo: " + e.getMessage());
        }
    }
}
