package entity;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Labirinto extends JFrame {
    private int[][] labirinto;
    private JPanel[][] cells;
    private int playerRow, playerCol;
    private Random random = new Random();

    public Labirinto(int[][] labirinto) {
        this.labirinto = labirinto;
        this.cells = new JPanel[labirinto.length][labirinto[0].length];
        initUI();
        iniciarAnimacao();
    }

    private void initUI() {
        // Configuração da janela e layout
        setTitle("Labirinto com Animação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(labirinto.length, labirinto[0].length));

        // Inicializa o labirinto gráfico
        for (int i = 0; i < labirinto.length; i++) {
            for (int j = 0; j < labirinto[i].length; j++) {
                cells[i][j] = new JPanel();
                if (labirinto[i][j] == 0) {
                    cells[i][j].setBackground(Color.BLACK); // Paredes são pretas
                } else {
                    cells[i][j].setBackground(Color.WHITE); // Passagens são brancas
                }
                add(cells[i][j]);
            }
        }

        // Define a posição inicial do "jogador" em uma passagem aleatória
       // do {
          //  playerRow = random.nextInt(labirinto.length);
        //    playerCol = random.nextInt(labirinto[0].length);
      //  } while (labirinto[playerRow][playerCol] == 0); // Garante que o "jogador" comece em uma passagem
        // define a posição inicial como (0,0)
        playerRow = 0;
        playerCol = 0;
        cells[playerRow][playerCol].setBackground(Color.RED); // Cor do "jogador"
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarAnimacao() {
        // Cria um timer que move o "jogador" a cada 500 milissegundos
        Timer timer = new Timer(250, e -> moverJogador());
        timer.start();
    }

    private void moverJogador() {
        // Escolhe uma direção aleatória: 0 = cima, 1 = baixo, 2 = esquerda, 3 = direita
        int direcao = random.nextInt(4);
        int novaLinha = playerRow;
        int novaColuna = playerCol;

        switch (direcao) {
            case 0: // Cima
                novaLinha = playerRow - 1;
                break;
            case 1: // Baixo
                novaLinha = playerRow + 1;
                break;
            case 2: // Esquerda
                novaColuna = playerCol - 1;
                break;
            case 3: // Direita
                novaColuna = playerCol + 1;
                break;
        }

        // Verifica se a nova posição está dentro dos limites e se não é uma parede
        if (novaLinha >= 0 && novaLinha < labirinto.length &&
            novaColuna >= 0 && novaColuna < labirinto[0].length &&
            labirinto[novaLinha][novaColuna] == 1) {

            // Remove o jogador da posição atual
            cells[playerRow][playerCol].setBackground(Color.WHITE);

            // Move o jogador para a nova posição
            playerRow = novaLinha;
            playerCol = novaColuna;
            cells[playerRow][playerCol].setBackground(Color.RED);
        }
    }

    // Método para criar um exemplo de labirinto
    public static int[][] criarLabirinto() {
        return new int[][]{
        	{1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 0},
            {0, 0, 1, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 1}
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Labirinto lab = new Labirinto(criarLabirinto());
        });
    	// Labirinto lab = new Labirinto(criarLabirinto());
    }
}

