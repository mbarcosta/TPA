package grafos;

import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.util.List;
public class Mapa {

    private Graph<String, String> grafo;
    private int contadorArestas;

    public Mapa() {
        //this.grafo = new SparseMultigraph<>();
    	 this.grafo = new DirectedSparseGraph<>();
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

    public void carregarMapaDeArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo do mapa");

        int retorno = fileChooser.showOpenDialog(null);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                // Ler o número total de cidades
                int numeroDeCidades = Integer.parseInt(br.readLine().trim());

                // Ler os nomes das cidades e adicionar ao grafo
                String linhaCidades = br.readLine();
                String[] cidades = linhaCidades.split(",\\s*");
                for (String cidade : cidades) {
                    adicionarCidade(cidade.trim());
                }

                // Ler a matriz de adjacências e conectar as cidades
                for (int i = 0; i < numeroDeCidades; i++) {
                    String[] distancias = br.readLine().split(",\\s*");
                    for (int j = 0; j < distancias.length; j++) {
                        if (i != j) { // Evitar loops na mesma cidade
                            double distancia = Double.parseDouble(distancias[j].trim());
                            if (distancia > 0) {
                                conectarCidades(cidades[i].trim(), cidades[j].trim(), distancia);
                            }
                        }
                    }
                }

                System.out.println("Mapa carregado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Erro no formato do arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum arquivo foi selecionado.");
        }
    }
   

    public void encontrarCircuitoHamiltoniano(String cidadeInicial) {
        // Verifica se a cidade inicial está no grafo
        if (!grafo.containsVertex(cidadeInicial)) {
            System.out.println("A cidade inicial não está no grafo.");
            return;
        }

        // Inicializa variáveis
        List<String> rotaAtual = new ArrayList<>();
        List<String> menorRota = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        double[] menorCusto = {Double.MAX_VALUE}; // Menor custo encontrado
        double[] custoAtual = {0.0}; // Custo atual da rota

        // Inicia a busca recursiva
        visita(cidadeInicial, cidadeInicial, visitados, rotaAtual, menorRota, custoAtual, menorCusto);

        // Exibe o resultado
        if (menorRota.isEmpty()) {
            System.out.println("Não foi possível encontrar um circuito Hamiltoniano.");
        } else {
            System.out.println("Menor Custo: " + menorCusto[0]);
            System.out.println("Menor Rota: " + String.join(" -> ", menorRota));
        }
    }

    private void visita(String cidadeAtual, String cidadeInicial, Set<String> visitados,
                        List<String> rotaAtual, List<String> menorRota, double[] custoAtual, double[] menorCusto) {
        // Marca a cidade como visitada
        visitados.add(cidadeAtual);
        rotaAtual.add(cidadeAtual);

        // Caso base: Todas as cidades foram visitadas
        if (visitados.size() == grafo.getVertexCount()) {
            // Retorna à cidade inicial para fechar o ciclo
            if (grafo.findEdge(cidadeAtual, cidadeInicial) != null) {
                double custoFinal = custoAtual[0] + getPesoAresta(cidadeAtual, cidadeInicial);
                if (custoFinal < menorCusto[0]) {
                    menorCusto[0] = custoFinal;

                    // Atualiza a menor rota
                    menorRota.clear();
                    menorRota.addAll(rotaAtual);
                    menorRota.add(cidadeInicial); // Fecha o ciclo
                }
            }
        } else {
            // Explora os vértices adjacentes
            for (String cidadeAdjacente : grafo.getNeighbors(cidadeAtual)) {
                if (!visitados.contains(cidadeAdjacente)) {
                    double pesoAresta = getPesoAresta(cidadeAtual, cidadeAdjacente);
                    custoAtual[0] += pesoAresta;

                    // Chamada recursiva
                    visita(cidadeAdjacente, cidadeInicial, visitados, rotaAtual, menorRota, custoAtual, menorCusto);

                    // Backtracking
                    custoAtual[0] -= pesoAresta;
                }
            }
        }

        // Remove a cidade atual da rota e desmarca como visitada
        rotaAtual.remove(rotaAtual.size() - 1);
        visitados.remove(cidadeAtual);
    }

    // Função auxiliar para obter o peso de uma aresta
    private double getPesoAresta(String cidade1, String cidade2) {
        String aresta = grafo.findEdge(cidade1, cidade2);
        if (aresta != null) {
            // Extrair o peso da aresta do formato "Estrada X (Y km)"
            String pesoStr = aresta.substring(aresta.indexOf("(") + 1, aresta.indexOf(" km"));
            return Double.parseDouble(pesoStr);
        }
        return Double.MAX_VALUE; // Retorna um valor alto para arestas inexistentes
    }

    // Função auxiliar para converter uma lista em um mapa para exibição
    private Map<String, String> toMap(List<String> rota) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < rota.size() - 1; i++) {
            map.put(rota.get(i), rota.get(i + 1));
        }
        return map;
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
            System.out.println("6. Carregar Mapa de Arquivo");
            System.out.println("7. Encontrar Circuito Hamiltoniano (TSP)");
            System.out.println("8. Sair");
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
                    mapa.carregarMapaDeArquivo();
                    break;
                case 7:
                    System.out.print("Digite a cidade inicial para o TSP: ");
                    String cidadeInicial = scanner.nextLine();
                    mapa.encontrarCircuitoHamiltoniano(cidadeInicial);
                    break;
                case 8:
                    System.out.println("Encerrando o programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
