package entity;

import java.util.function.Consumer;
public class PilhaS<T> implements Pilha<T> {
    private Object[] stack;
    private int max;
    private int topo;

    // Construtor
    public PilhaS(int size) {
        max = size;
        stack = new Object[max];
        topo = -1; // Pilha vazia
    }

    @Override
    public void empilhe(T v) throws FaltaEspaco {
        if (topo == max - 1) {
            throw new FaltaEspaco("Pilha cheia! Não é possível empilhar.");
        }
        stack[++topo] = v;
    }
    @Override
    @SuppressWarnings("unchecked")
    public T desempilhe() throws FaltaElemento {
        if (topo == -1) {
            throw new FaltaElemento("Pilha vazia! Não é possível desempilhar.");
        }
        return (T) stack[topo--];
    }

    @Override
    public boolean vazia() {
        return topo == -1;
    }
    public int tamanho() {
        return topo + 1; // Retorna o número de elementos na pilha
    }
    @Override
    @SuppressWarnings("unchecked")
    public void processaElementos(Consumer<T> acao) {
        for (int i = 0; i <= topo; i++) {
            acao.accept((T) stack[i]);
        }
    }
}

