package entity.cms;

import java.io.IOException;
import java.util.List;

public interface ArquivoSequencial<T> {

    // Abre o arquivo especificado no modo de leitura, escrita ou leitura/escrita.
    void abrirArquivo(String nomeDoArquivo, String modoDeLeitura, Class<T> classeBase) throws IOException;

    // Lê múltiplos registros do arquivo e retorna como uma lista de objetos do tipo T.
    List<T> leiaDoArquivo(int numeroDeRegistros) throws IOException, ClassNotFoundException;

    // Escreve uma lista de registros no arquivo.
    void escreveNoArquivo(List<T> dados) throws IOException;

    // Fecha o arquivo de forma segura, liberando os recursos.
    void fechaArquivo() throws IOException;
}
