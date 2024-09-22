package entity;

import java.util.Scanner;

public class Curso {
    private Pilha<Aluno> pilhaAlunos;
    private int maxAlunos;

    public Curso(int maxAlunos) {
        this.maxAlunos = maxAlunos;
        this.pilhaAlunos = new PilhaS<>(maxAlunos); // Instancia a pilha de alunos
    }

    // Método para cadastrar um aluno
    public void cadastrarAluno(Scanner scanner) {
        System.out.println("Insira o nome do aluno:");
        String nome = scanner.nextLine();
        Aluno aluno = new Aluno(nome);
        try {
            pilhaAlunos.empilhe(aluno);
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (FaltaEspaco e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Método para indicar o número máximo de alunos e retirar excessos
    public void indicarNumeroMaximo() {
    	 Scanner scanner = new Scanner(System.in);
         System.out.print("Digite o número máximo de Alunos: ");
         int maxAlunos = scanner.nextInt();
        while (!pilhaAlunos.vazia() && (pilhaAlunos.tamanho() > maxAlunos)) {
            try {
                Aluno alunoRemovido = (Aluno) pilhaAlunos.desempilhe();
                System.out.println("Aluno removido: " + alunoRemovido.getNome());
            } catch (FaltaElemento e) {
                System.out.println("Erro ao remover aluno: " + e.getMessage());
            }
        }
    }

    // Método para exibir menu
    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Indicar Número Máximo de Alunos");
            System.out.println("3. Imprimir nomes.");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner);
                    break;
                case 2:
                    indicarNumeroMaximo();
                    break;
                case 3:
                    this.pilhaAlunos.processaElementos(a->a.printNome(a));
                    break;
                case 4:
                    System.out.println("Fim do Programa");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 3);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número máximo de Candidatos: ");
        int maxAlunos = scanner.nextInt();
        Curso curso = new Curso(maxAlunos);
        curso.exibirMenu();
    }
}

