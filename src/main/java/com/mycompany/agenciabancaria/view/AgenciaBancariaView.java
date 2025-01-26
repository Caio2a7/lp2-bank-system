package com.mycompany.agenciabancaria.view;

import com.mycompany.agenciabancaria.controller.AgenciaController;
import com.mycompany.agenciabancaria.model.Titular;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import com.mycompany.agenciabancaria.model.ArquivoUtil;

public class AgenciaBancariaView {

    private final AgenciaController controller;
    private final Scanner scanner;

    public AgenciaBancariaView() {
        controller = new AgenciaController();
        scanner = new Scanner(System.in);
    }

    /**
     * Exibe o menu principal de opções.
     */
    public void exibirMenu() {
        while (true) {
            System.out.println("####### Agência Bancária #######");
            System.out.println("1 - Cadastro");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Realiza o cadastro de um novo usuário.
     */
    public void cadastrarUsuario() {
        System.out.println("============= Cadastro =============");
        System.out.print("Informe seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Informe sua senha: ");
        String senha = scanner.nextLine();

        if (controller.cadastrarUsuario(nome, cpf, senha)) {
            System.out.println("Usuário cadastrado com sucesso!");
            exibirMenu();
        } else {
            System.out.println("Usuário já cadastrado! Tente realizar Login.");
            exibirMenu();
        }
    }

    /**
     * Realiza o login do usuário.
     */
    public void login() {
        System.out.println("=============== Login ==============");
        System.out.print("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Informe sua senha: ");
        String senha = scanner.nextLine();
    
        try {
            // Carrega os usuários do arquivo
            List<Titular> usuarios = ArquivoUtil.carregarUsuarios("data/usuarios.csv");
    
            // Autentica o usuário
            Titular usuario = controller.autenticarUsuario(cpf, senha, usuarios);
    
            if (usuario != null) {
                System.out.println("Bem vind@, " + usuario.getNome() + "!");
                exibirMenuOperacoes();
            } else {
                System.out.println("Credenciais inválidas, verifique o CPF ou senha.");
                voltarMenu();
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
    

    /**
     * Exibe o menu de operações do usuário logado.
     */
    private void exibirMenuOperacoes() {
        System.out.println("====================================");
        System.out.println("1 - Verificar Saldo");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Transferir");
        System.out.println("5 - Cadastrar nova conta");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma operação: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                verificarSaldo();
                break;
            case 2:
                depositar();
                break;
            case 3:
                sacar();
                break;
            case 4:
                transferir();
                break;
            case 5:
                cadastrarConta();
                break;
            case 6:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    /**
     * Verifica o saldo de uma conta.
     */
    public void verificarSaldo() {
        double saldo = -1;
        if (controller.getUsuarioLogado() != null) {
            System.out.println("====================================");
            System.out.print("Informe o número da conta que deseja consultar: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();

            saldo = controller.getSaldo(numeroConta);

        } else {
            System.out.println("Usuário não cadastrado");
        }

        if (saldo != -1) {
            System.out.println("O saldo disponível é R$" + saldo);
            voltarMenu();
        } else {
            System.out.println("Usuário ou conta não encontrados");
            voltarMenu();
        }
    }

    /**
     * Realiza um depósito em uma conta.
     */
    public void depositar() {
        System.out.println("============ Depósito ==============");
        System.out.print("Informe o número da conta em que deseja depositar: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o valor para depósito: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("O valor precisa ser maior que 0.");
            voltarMenu();
        } else if (controller.depositar(valor, numeroConta)) {
            System.out.println("Depósito realizado com sucesso!");
            voltarMenu();
        } else {
            System.out.println("Depósito mal sucedido, tente novamente!");
            voltarMenu();
        }
    }

    /**
     * Realiza um saque de uma conta.
     */
    public void sacar() {
        System.out.println("====================================");
        System.out.print("Informe o número da conta em que deseja sacar: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Informe o valor que deseja sacar: ");
        double valorSaque = scanner.nextDouble();

        if (valorSaque <= 0) {
            System.out.println("O valor precisa ser maior que 0.");
            exibirMenu();
        } else if (controller.sacar(valorSaque, numeroConta)) {
            System.out.println("Valor de R$" + valorSaque + " sacado com sucesso!");
            exibirMenu();
        } else {
            System.out.println("Algo deu errado!");
            exibirMenu();
        }
    }

    /**
     * Realiza uma transferência entre contas.
     */
    public void transferir() {
        System.out.println("========== Transferência ===========");
        System.out.print("Informe o número da conta remetente da transferência: ");
        int contaRemetente = scanner.nextInt();
        System.out.print("Informe o número da conta destinatária da transferência: ");
        int contaDestinataria = scanner.nextInt();
        System.out.print("Informe o valor que deseja transferir: ");
        double valorTrans = scanner.nextDouble();

        if (controller.transferir(valorTrans, contaRemetente, contaDestinataria)) {
            System.out.println("Valor de R$" + valorTrans + " transferido para a conta " + contaDestinataria + " com sucesso!");
        } else {
            System.out.println("Algo deu errado!");
        }
    }

    /**
     * Realiza o cadastro de uma nova conta.
     */
    public void cadastrarConta() {
        System.out.println("====================================");
        System.out.print("Informe o número da conta que deseja cadastrar: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Informe o saldo inicial da conta: ");
        double saldoInicial = scanner.nextDouble();
        System.out.println("Informe o tipo:");
        System.out.println("1 - Corrente");
        System.out.println("2 - Poupança:");
        int tipo = scanner.nextInt();

        if (controller.criarNovaConta(numeroConta, saldoInicial, tipo)) {
            System.out.println("Conta cadastrada com sucesso!");
            voltarMenu();
        } else {
            System.out.println("Algo deu errado!");
            voltarMenu();
        }
    }

    /**
     * Exibe a opção para voltar ao menu principal.
     */
    public void voltarMenu() {
        System.out.println("====================================");
        System.out.println("Deseja voltar para o menu principal?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.print("Resposta:");
        int opc = scanner.nextInt();

        if (opc == 1) {
            exibirMenu();
        } else {
            System.exit(0);
        }
    }
}
