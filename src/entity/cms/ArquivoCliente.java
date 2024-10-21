package entity.cms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoCliente implements ArquivoSequencial<Cliente> {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private File file;
    
    @Override
    public void abrirArquivo(String nomeDoArquivo, String modoDeLeitura, Class<Cliente> classeBase) throws IOException {
        this.file = new File(nomeDoArquivo);
        
        if (modoDeLeitura.equals("leitura")) {
            if (file.exists()) {
                inputStream = new ObjectInputStream(new FileInputStream(file));
            } else {
                throw new FileNotFoundException("Arquivo não encontrado.");
            }
        } else if (modoDeLeitura.equals("escrita")) {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
        } else if (modoDeLeitura.equals("leitura/escrita")) {
            // Para leitura/escrita, abrir ambos os streams
            if (file.exists()) {
                inputStream = new ObjectInputStream(new FileInputStream(file));
            }
            outputStream = new ObjectOutputStream(new FileOutputStream(file, true)); // Modo append
        } else {
            throw new IllegalArgumentException("Modo de leitura inválido.");
        }
    }

    @Override
    public List<Cliente> leiaDoArquivo(int numeroDeRegistros) throws IOException, ClassNotFoundException {
        List<Cliente> registros = new ArrayList<>();
        
        try {
            for (int i = 0; i < numeroDeRegistros; i++) {
                Cliente cliente = (Cliente) inputStream.readObject();
                registros.add(cliente);
            }
        } catch (EOFException e) {
            // Final do arquivo atingido, retornando o que foi lido até agora
        }
        
        return registros;
    }

    @Override
    public void escreveNoArquivo(List<Cliente> dados) throws IOException {
        for (Cliente cliente : dados) {
            outputStream.writeObject(cliente);
        }
    }

    @Override
    public void fechaArquivo() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
