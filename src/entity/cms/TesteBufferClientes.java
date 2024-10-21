package entity.cms;

public class TesteBufferClientes {

    public static void main(String[] args) {

        // Criando o buffer de clientes
        BufferDeClientes bufferDeClientes = new BufferDeClientes();

        // Associa o buffer a um arquivo sequencial de clientes
        ArquivoCliente arquivoCliente = new ArquivoCliente();
        bufferDeClientes.associaBuffer(arquivoCliente);

        // Inicializa o buffer para escrita
        bufferDeClientes.inicializaBuffer("escrita", "teste2.dat");

        // Adiciona clientes ao buffer e escreve no arquivo
        for (int i = 0; i < 1000; i++) {
            Cliente cliente = new Cliente("Nome" + i, "Sobrenome" + i, "Endereço" + i, "Telefone" + i, i % 100);
            bufferDeClientes.adicionaAoBuffer(cliente);
        }

        // Fecha o buffer e o arquivo
        bufferDeClientes.fechaBuffer();

        // Inicializa o buffer para leitura
        bufferDeClientes.inicializaBuffer("leitura","teste2.dat");

        // Lê clientes do arquivo via buffer
        Cliente clienteLido;
        while ((clienteLido = bufferDeClientes.proximoCliente()) != null) {
            System.out.println(clienteLido);
        }

        // Fecha o buffer e o arquivo
        bufferDeClientes.fechaBuffer();
    }
}
