package com.mycompany.agenciabancaria.controller;

import com.mycompany.agenciabancaria.model.Conta;
import com.mycompany.agenciabancaria.model.ContaCorrente;
import com.mycompany.agenciabancaria.model.ContaPoupanca;
import com.mycompany.agenciabancaria.model.Titular;
import java.util.ArrayList;
import java.util.List;

public class AgenciaController {
    private List<Titular> usuarios = new ArrayList<>();
    private Titular usuarioLogado;

    /**
     * Método para cadastrar um novo usuário na agência bancária.
     * 
     * @param nome  Nome do novo titular
     * @param cpf   CPF do novo titular
     * @param senha Senha do novo titular
     * @return      Retorna true se o usuário foi cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrarUsuario(String nome, String cpf, String senha) {
        for (Titular titular : usuarios) {
            if (titular.getCPF().equals(cpf)) {
                return false; // CPF já cadastrado
            }
        }
        usuarios.add(new Titular(nome, cpf, senha));
        return true; // Usuário cadastrado com sucesso
    }

    /**
     * Método para autenticar um usuário na agência bancária.
     * 
     * @param cpf   CPF do usuário
     * @param senha Senha do usuário
     * @return      Retorna o titular logado se as credenciais estiverem corretas, null caso contrário
     */
    public Titular autenticarUsuario(String cpf, String senha) {
        for (Titular usuario : usuarios) {
            if (usuario.getCPF().equals(cpf) && usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                return usuarioLogado; // Usuário autenticado
            }
        }
        return null; // Credenciais inválidas
    }

    /**
     * Método para obter o titular que está atualmente logado.
     * 
     * @return Retorna o titular logado, ou null se nenhum titular estiver logado
     */
    public Titular getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Método para obter o saldo de uma conta específica do usuário logado.
     * 
     * @param numeroConta Número da conta
     * @return            Retorna o saldo da conta, ou -1 se a conta não for encontrada
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
     * 
     * @param valor       Valor do depósito
     * @param numeroConta Número da conta
     * @return            Retorna true se o depósito foi realizado com sucesso, false caso contrário
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
     * 
     * @param valor       Valor do saque
     * @param numeroConta Número da conta
     * @return            Retorna true se o saque foi realizado com sucesso, false caso contrário
     */
    public boolean sacar(double valor, int numeroConta) {
        for (Conta conta : usuarioLogado.getContas()) {
            if (conta.getNumero() == numeroConta && valor <= conta.getSaldo()) {
                conta.Sacar(valor);
                return true; // Saque realizado com sucesso
            }
        }
        return false; // Conta não encontrada ou saldo insuficiente
    }

    /**
     * Método para realizar uma transferência entre contas do usuário logado.
     * 
     * @param valor             Valor da transferência
     * @param contaRemetente     Número da conta remetente
     * @param contaDestinataria  Número da conta destinatária
     * @return                  Retorna true se a transferência foi realizada com sucesso, false caso contrário
     */
    public boolean transferir(double valor, int contaRemetente, int contaDestinataria) {
        Conta contaOrigem = null;
        Conta contaDestino = null;

        // Procurando a conta de origem na lista de contas do usuário logado
        for (Conta conta : usuarioLogado.getContas()) {
            if (conta.getNumero() == contaRemetente) {
                contaOrigem = conta;
                break;
            }
        }

        // Procurando a conta de destino na lista de contas dos usuários da aplicação
        for (Titular usuario : usuarios) {
            for (Conta conta : usuario.getContas()) {
                if (conta.getNumero() == contaDestinataria) {
                    contaDestino = conta;
                    break;
                }
            }
            if (contaDestino != null) {
                break;
            }
        }

        // Realizando a transferência
        if (contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valor) {
            contaOrigem.transferir(valor, contaDestino);
            return true; // Transferência realizada com sucesso
        } else {
            return false; // Erro na transferência
        }
    }

    /**
     * Método para criar uma nova conta para o usuário logado.
     * 
     * @param numero      Número da nova conta
     * @param saldoInicial Saldo inicial da nova conta
     * @param tipo        Tipo da conta (1 para Conta Corrente, 2 para Conta Poupança)
     * @return            Retorna true se a conta foi criada com sucesso, false caso contrário
     */
    public boolean criarNovaConta(int numero, double saldoInicial, int tipo) {
        if (usuarioLogado != null) {
            Conta novaConta = null;
            boolean exist = false;

            // Verificando se a conta com o mesmo número já existe
            for (Conta conta : usuarioLogado.getContas()) {
                if (conta.getNumero() == numero) {
                    exist = true;
                    break;
                }
            }

            // Criando nova conta de acordo com o tipo
            if (tipo == 1 && !exist) {
                novaConta = new ContaCorrente(numero, saldoInicial, usuarioLogado);
            } else if (tipo == 2 && !exist) {
                novaConta = new ContaPoupanca(numero, saldoInicial, usuarioLogado);
            }

            // Adicionando a nova conta ao usuário logado
            usuarioLogado.AdicionarConta(novaConta);
            return true; // Nova conta criada com sucesso
        } else {
            return false; // Nenhum usuário logado
        }
    }
}
