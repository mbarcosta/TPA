package grafos;

import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Mapa {

    private Graph<String, String> grafo;
    private int contadorArestas;

    public Mapa() {
        this.grafo = new SparseMultigraph<>();
        this.contadorArestas = 0;
    }

    public void adicionarCidade(String cidade) {
        if (grafo.addVertex(cidade)) {
            System.out.println("Cidade " + cidade + " adicionada com sucesso.");
        } else {
            System.out.println("Cidade " + cidade + " já existe no mapa.");
        }
    }

    public void removerCidade(String cidade) {
        if (grafo.removeVertex(cidade)) {
            System.out.println("Cidade " + cidade + " removida com sucesso.");
        } else {
            System.out.println("Cidade " + cidade + " não existe no mapa.");
        }
    }

    public void conectarCidades(String cidade1, String cidade2, double distancia) {
        if (grafo.containsVertex(cidade1) && grafo.containsVertex(cidade2)) {
            String aresta = "Estrada " + contadorArestas++ + " (" + distancia + " km)";
            grafo.addEdge(aresta, cidade1, cidade2);
            System.out.println("Conexão entre " + cidade1 + " e " + cidade2 + " adicionada com sucesso.");
        } else {
            System.out.println("Uma ou ambas as cidades não existem no mapa.");
        }
    }

    public void imprimirMapa() {
        System.out.println("Mapa de Cidades:");
        for (String cidade : grafo.getVertices()) {
            System.out.println("Cidade: " + cidade);
        }
        System.out.println("Conexões:");
        for (String aresta : grafo.getEdges()) {
            System.out.println("Conexão: " + aresta + " entre " + grafo.getEndpoints(aresta));
        }
    }

    public void mostrarMapaGraficamente() {
        JFrame frame = new JFrame("Mapa de Cidades");
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CircleLayout<String, String> layout = new CircleLayout<>(grafo);
        VisualizationImageServer<String, String> viz = new VisualizationImageServer<>(layout, new Dimension(600, 600));
        viz.getRenderContext().setEdgeLabelTransformer((Transformer<String, String>) aresta -> aresta);
        viz.getRenderContext().setVertexLabelTransformer((Transformer<String, String>) cidade -> cidade);
        viz.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(viz, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Mapa mapa = new Mapa();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu de Opções:");
            System.out.println("1. Adicionar Cidade");
            System.out.println("2. Remover Cidade");
            System.out.println("3. Conectar Cidades");
            System.out.println("4. Imprimir Mapa");
            System.out.println("5. Mostrar Mapa Graficamente");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome da cidade: ");
                    String cidade = scanner.nextLine();
                    mapa.adicionarCidade(cidade);
                    break;
                case 2:
                    System.out.print("Digite o nome da cidade: ");
                    cidade = scanner.nextLine();
                    mapa.removerCidade(cidade);
                    break;
                case 3:
                    System.out.print("Digite o nome da primeira cidade: ");
                    String cidade1 = scanner.nextLine();
                    System.out.print("Digite o nome da segunda cidade: ");
                    String cidade2 = scanner.nextLine();
                    System.out.print("Digite a distância entre as cidades: ");
                    double distancia = scanner.nextDouble();
                    scanner.nextLine();
                    mapa.conectarCidades(cidade1, cidade2, distancia);
                    break;
                case 4:
                    mapa.imprimirMapa();
                    
                    break;
                case 5:
                    mapa.mostrarMapaGraficamente();
                    break;
                case 6:
                    System.out.println("Encerrando o programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
