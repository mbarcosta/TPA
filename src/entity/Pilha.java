package entity;
import java.util.function.Consumer;
public interface Pilha<T> {
    void empilhe(T v) throws FaltaEspaco;
    T desempilhe() throws FaltaElemento;
    boolean vazia();
    int tamanho();
    /* metodo para processar elementos da pilha de forma generica */ 
    void processaElementos(Consumer<T> acao);
}