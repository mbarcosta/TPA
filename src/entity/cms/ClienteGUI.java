package entity.cms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteGUI {

    private BufferDeClientes bufferDeClientes;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    public ClienteGUI() {
        bufferDeClientes = new BufferDeClientes();
        criarInterface();
    }

    private void criarInterface() {
        JFrame frame = new JFrame("Gerenciador de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Painel de controle
        JPanel painelControle = new JPanel();
        JButton btnCarregar = new JButton("Carregar Clientes");
        painelControle.add(btnCarregar);
        frame.add(painelControle, BorderLayout.NORTH);

        // Modelo da tabela
        modeloTabela = new DefaultTableModel(new Object[]{"#", "Nome", "Sobrenome", "Endereço", "Telefone", "CreditScore"}, 0);
        tabelaClientes = new JTable(modeloTabela) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição das células
            }
        };

        // Ajusta a largura da primeira coluna
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(30);

        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Ação do botão
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarClientes();
            }
        });

        frame.setVisible(true);
    }

    private void carregarClientes() {
        // Solicita o nome do arquivo
        String nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo de clientes:");

        if (nomeArquivo != null && !nomeArquivo.trim().isEmpty()) {
            // Inicializa o buffer e carrega os dados usando ArquivoCliente
            bufferDeClientes.associaBuffer(new ArquivoCliente());
            bufferDeClientes.inicializaBuffer("leitura", nomeArquivo);

            modeloTabela.setRowCount(0); // Limpa a tabela antes de carregar novos dados

            // Lê os clientes do buffer e adiciona à tabela
            Cliente cliente;
            int contador = 1; // Contador de linhas
            while ((cliente = bufferDeClientes.proximoCliente()) != null) {
                modeloTabela.addRow(new Object[]{contador++, cliente.getNome(), cliente.getSobrenome(), cliente.getEndereco(), cliente.getTelefone(), cliente.getCreditScore()});
            }

            // Fecha o buffer
            bufferDeClientes.fechaBuffer();
        } else {
            JOptionPane.showMessageDialog(null, "Nome do arquivo não pode ser vazio.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteGUI::new);
    }
}

