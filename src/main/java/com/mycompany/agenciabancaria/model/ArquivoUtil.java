package com.mycompany.agenciabancaria.model;

import com.mycompany.agenciabancaria.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {
    public static List<Titular> carregarUsuarios(String caminhoArquivo) throws IOException {
        List<Titular> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0];
                String cpf = partes[1];
                String senha = partes[2];
                usuarios.add(new Titular(nome, cpf, senha));
            }
        }
        System.out.println("Usuários carregados: " + usuarios.size());
        return usuarios;
    }
    
    
    public static void salvarUsuarioEmArquivo(Titular usuario, String caminhoArquivo) throws IOException {
        // Verifica se o diretório "data" existe, se não, cria o diretório
        File diretorio = new File("data");
        if (!diretorio.exists()) {
            diretorio.mkdir(); // Cria o diretório
        }

        // Abrindo o arquivo CSV em modo append (adicionar ao final)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            // Escrevendo o usuário no formato CSV: nome, cpf, senha
            writer.write(usuario.getNome() + "," + usuario.getCPF() + "," + usuario.getSenha());
            writer.newLine();  // Adicionando uma nova linha no arquivo
        }
    }
    public static void salvarContasEmArquivo(Titular usuario, String caminhoArquivo) throws IOException {
        // Verifica se o diretório "data" existe, se não, cria o diretório
        File diretorio = new File("data");
        if (!diretorio.exists()) {
            diretorio.mkdir(); // Cria o diretório
        }

        // Abrindo o arquivo CSV em modo append (adicionar ao final)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            // Salvando as contas de um usuário
            for (Conta conta : usuario.getContas()) {
                String tipoConta = conta.getTipo();  // Aqui pegamos o tipo diretamente
                writer.write(String.format("%s,%d,%s,%.2f,%s\n",
                        usuario.getNome(),
                        conta.getNumero(),
                        conta.getDono().getNome(),
                        conta.getSaldo(),
                        tipoConta));
            }
        }
    }
    
    public static List<Conta> carregarContas(String caminhoArquivo, Titular usuario) throws IOException {
        List<Conta> contas = new ArrayList<>();
        
        // Abrindo o arquivo CSV para leitura
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            // Lendo linha por linha
            while ((linha = reader.readLine()) != null) {
                // Dividindo a linha em partes com base na vírgula
                String[] partes = linha.split(",");
                String nomeTitular = partes[2];  // Nome do titular da conta
                int numeroConta = Integer.parseInt(partes[1]);  // Número da conta
                double saldo = Double.parseDouble(partes[3]);  // Saldo da conta
                String tipoConta = partes[4];  // Tipo de conta (Corrente, Poupança, Salário)
                
                // Verifica se a conta é do titular que estamos buscando
                if (usuario.getNome().equals(nomeTitular)) {
                    // Cria a conta com base nas informações
                    Conta conta = criarConta(tipoConta, numeroConta, saldo, usuario);
                    contas.add(conta);  // Adiciona a conta à lista de contas
                }
            }
        }
        
        return contas;  // Retorna a lista de contas do titular
    }
    public static boolean verificarCredenciais(String cpf, String senha, String caminhoArquivo) throws IOException {
        // Lê o arquivo CSV para verificar as credenciais
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            // Lê cada linha do arquivo CSV
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                
                // Verifica se o CPF e a senha correspondem
                String cpfNoArquivo = partes[1];
                String senhaNoArquivo = partes[2];
                
                if (cpfNoArquivo.equals(cpf) && senhaNoArquivo.equals(senha)) {
                    return true; // Credenciais válidas
                }
            }
        }
        
        return false; // Credenciais inválidas
    }
    
    // Salva as agências, contas e seus dados em um arquivo CSV
    public static void salvarAgencias(List<Agencia> agencias, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Agencia agencia : agencias) {
                for (Conta conta : agencia.getContas()) {
                    String tipoConta = conta.getTipo();  // Aqui pegamos o tipo diretamente
                    writer.write(String.format("%s,%d,%s,%.2f,%s\n",
                            agencia.getNome(),
                            conta.getNumero(),
                            conta.getDono().getNome(),
                            conta.getSaldo(),
                            tipoConta));
                }
            }
        }
    }

    // Carrega as agências e contas a partir de um arquivo CSV
    public static List<Agencia> carregarAgencias(String caminhoArquivo) throws IOException {
        List<Agencia> agencias = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String nomeAgencia = partes[0];
                int numeroConta = Integer.parseInt(partes[1]);
                String nomeTitular = partes[2];
                double saldo = Double.parseDouble(partes[3]);
                String tipoConta = partes[4];

                // Criação da agência, caso ainda não exista
                Agencia agencia = agencias.stream()
                        .filter(a -> a.getNome().equals(nomeAgencia))
                        .findFirst()
                        .orElseGet(() -> {
                            Agencia novaAgencia = new Agencia(nomeAgencia);
                            agencias.add(novaAgencia);
                            return novaAgencia;
                        });

                // Criar o Titular com valores fictícios para CPF e senha
                Titular titular = new Titular(nomeTitular, "CPF_FICTICIO", "SENHA_FICTICIA");

                // Criação da conta com base no tipo lido do arquivo
                Conta conta = criarConta(tipoConta, numeroConta, saldo, titular);

                // Adiciona a conta à agência
                agencia.adicionarConta(conta);
            }
        }
        return agencias;
    }

    // Criação de diferentes tipos de contas com base no tipo lido do arquivo
    private static Conta criarConta(String tipoConta, int numero, double saldo, Titular titular) {
        switch (tipoConta) {
            case "Corrente":
                return new ContaCorrente(numero, saldo, titular);
            case "Poupança":
                return new ContaPoupanca(numero, saldo, titular);
            case "Salário":
                return new ContaSalario(numero, saldo, titular);
            default:
                throw new RuntimeException("Tipo de conta desconhecido: " + tipoConta);
        }
    }
}
