import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ======================== CLASSE USUARIO ========================
class Usuario {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String endereco;

    public Usuario(String nome, String cpf, String dataNascimento, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
               "\nCPF: " + cpf +
               "\nData de Nascimento: " + dataNascimento +
               "\nEndereço: " + endereco + "\n";
    }
}

// ======================== CLASSE CONTA ========================
class ContaBancaria {
    private static final double LIMITE_SAQUE = 500.0;
    private static final int LIMITE_SAQUES = 3;

    private String agencia;
    private int numeroConta;
    private Usuario titular;
    private double saldo;
    private StringBuilder extrato;
    private int numeroSaques;

    public ContaBancaria(String agencia, int numeroConta, Usuario titular) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.extrato = new StringBuilder();
        this.numeroSaques = 0;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public Usuario getTitular() {
        return titular;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            extrato.append(String.format("Depósito: R$ %.2f%n", valor));
            System.out.println("✅ Depósito realizado com sucesso!");
        } else {
            System.out.println("❌ Valor inválido.");
        }
    }

    public void sacar(double valor) {
        if (valor > saldo) {
            System.out.println("❌ Saldo insuficiente.");
        } else if (valor > LIMITE_SAQUE) {
            System.out.println("❌ Valor excede o limite de saque.");
        } else if (numeroSaques >= LIMITE_SAQUES) {
            System.out.println("❌ Número máximo de saques excedido.");
        } else if (valor > 0) {
            saldo -= valor;
            numeroSaques++;
            extrato.append(String.format("Saque: R$ %.2f%n", valor));
            System.out.println("✅ Saque realizado com sucesso!");
        } else {
            System.out.println("❌ Valor inválido.");
        }
    }

    public void exibirExtrato() {
        System.out.println("\n========== EXTRATO ==========");
        System.out.println(extrato.length() == 0 ? "Nenhuma movimentação." : extrato.toString());
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
        System.out.println("=============================\n");
    }

    @Override
    public String toString() {
        return String.format("Agência: %s%nConta: %d%nTitular: %s%nSaldo: R$ %.2f%n",
                agencia, numeroConta, titular.getNome(), saldo);
    }
}

// ======================== CLASSE BANCO ========================
class Banco {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<ContaBancaria> contas = new ArrayList<>();
    private static final String AGENCIA_PADRAO = "0001";

    private Scanner sc = new Scanner(System.in);

    public void criarUsuario() {
        System.out.print("Informe o CPF (somente números): ");
        String cpf = sc.nextLine();
        if (buscarUsuarioPorCPF(cpf) != null) {
            System.out.println("⚠️ Já existe um usuário com esse CPF.");
            return;
        }

        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("Data de nascimento (dd-mm-aaaa): ");
        String data = sc.nextLine();
        System.out.print("Endereço (logradouro, nº - bairro - cidade / UF): ");
        String endereco = sc.nextLine();

        usuarios.add(new Usuario(nome, cpf, data, endereco));
        System.out.println("✅ Usuário criado com sucesso!");
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("⚠️ Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\n===== LISTA DE USUÁRIOS =====");
        for (Usuario u : usuarios) {
            System.out.println(u);
            System.out.println("-----------------------------");
        }
    }

    public void criarConta() {
        System.out.print("Informe o CPF do usuário: ");
        String cpf = sc.nextLine();
        Usuario usuario = buscarUsuarioPorCPF(cpf);

        if (usuario == null) {
            System.out.println("❌ Usuário não encontrado.");
            return;
        }

        int numeroConta = contas.size() + 1;
        ContaBancaria conta = new ContaBancaria(AGENCIA_PADRAO, numeroConta, usuario);
        contas.add(conta);

        System.out.printf("✅ Conta %d criada para %s!%n", numeroConta, usuario.getNome());
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("⚠️ Nenhuma conta cadastrada.");
            return;
        }
        System.out.println("\n===== LISTA DE CONTAS =====");
        for (ContaBancaria c : contas) {
            System.out.println(c);
            System.out.println("---------------------------");
        }
    }

    public ContaBancaria selecionarConta() {
        listarContas();
        if (contas.isEmpty()) return null;

        System.out.print("Informe o número da conta: ");
        int numero = Integer.parseInt(sc.nextLine());

        for (ContaBancaria conta : contas) {
            if (conta.getNumeroConta() == numero) {
                return conta;
            }
        }
        System.out.println("❌ Conta não encontrada.");
        return null;
    }

    private Usuario buscarUsuarioPorCPF(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) return u;
        }
        return null;
    }
}

// ======================== CLASSE MAIN ========================
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();

        String opcao;
        do {
            System.out.println("""
                    
========= MENU =========
[d] Depositar
[s] Sacar
[e] Extrato
[u] Novo usuário
[lu] Listar usuários
[c] Nova conta
[l] Listar contas
[q] Sair
========================
=> """);

            opcao = sc.nextLine().toLowerCase();

            switch (opcao) {
                case "u" -> banco.criarUsuario();
                case "lu" -> banco.listarUsuarios();
                case "c" -> banco.criarConta();
                case "l" -> banco.listarContas();
                case "d" -> {
                    ContaBancaria conta = banco.selecionarConta();
                    if (conta != null) {
                        System.out.print("Valor do depósito: ");
                        double valor = Double.parseDouble(sc.nextLine());
                        conta.depositar(valor);
                    }
                }
                case "s" -> {
                    ContaBancaria conta = banco.selecionarConta();
                    if (conta != null) {
                        System.out.print("Valor do saque: ");
                        double valor = Double.parseDouble(sc.nextLine());
                        conta.sacar(valor);
                    }
                }
                case "e" -> {
                    ContaBancaria conta = banco.selecionarConta();
                    if (conta != null) {
                        conta.exibirExtrato();
                    }
                }
                case "q" -> System.out.println("👋 Saindo... Obrigado por usar o sistema bancário!");
                default -> System.out.println("❌ Opção inválida.");
            }
        } while (!opcao.equals("q"));
    }
}
