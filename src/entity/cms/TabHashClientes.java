package entity.cms; 
import com.github.javafaker.Faker;
import java.util.Arrays;
//1009, 1013, 1019, 1021 e 1031
//  10007, 10009, 10037, 10039 e 10061.
//  1000003, 1000033, 1000037, 1000039 e 1000081.
public class TabHashClientes {
	private static final int[] PRIMOS = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};
    private static final int MAXTAB = 10007; // Tamanho máximo da tabela hash
    private Cliente[] tabHash; // Array para armazenar os clientes
    private boolean[] ocupado; // Array para verificar se a posição está ocupada
    private int[] colisaoPorPosicao; // Array para contar colisões por posição

    // Construtor para inicializar a tabela hash
    public TabHashClientes() {
        tabHash = new Cliente[MAXTAB];
        ocupado = new boolean[MAXTAB];
        colisaoPorPosicao = new int[MAXTAB];
        Arrays.fill(ocupado, false); // Inicializa todas as posições como não ocupadas
        Arrays.fill(colisaoPorPosicao, 0); // Inicializa contagem de colisões
    }

    private int hash(String chave) {
    	return hash5(chave);
    }
    // Função hash para calcular a posição de um cliente com base no nome
    private int hash0(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += Math.abs(chave.charAt(i)) * (i + 1);
        }
        
     //   System.out.println("Valores de K: " + soma);
    //    System.out.println( "Chave: " + (soma % MAXTAB));    
        return soma % MAXTAB;
        }

    // Método para inserir um cliente na tabela hash
    public boolean create(Cliente cliente) {
        int pos = hash(cliente.getNome());
        if (!ocupado[pos]) {
            tabHash[pos] = cliente;
            ocupado[pos] = true;
            return true; // Inserção sem colisão
        } else {
            colisaoPorPosicao[pos]++; // Incrementa a contagem de colisões para a posição
            return false; // Colisão ocorreu
        }
    }

    // Método para testar a função hash e coletar estatísticas
    public void testaFuncaoHash(int n_registros) {
        Faker faker = new Faker();
        int tentativasInsercao = n_registros;
        int totalColisoes = 0;

        // Geração de clientes e inserção na tabela
        for (int i = 0; i < n_registros; i++) {
            String nome = faker.name().fullName();
            Cliente cliente = new Cliente(nome, "Sobrenome", "Endereço", "1234-5678", 50);
            if (!create(cliente)) {
                totalColisoes++;
            }
        }

        // Estatísticas
        double percentualColisoes = (double) totalColisoes / tentativasInsercao * 100;
        double mediaColisoesPorPosicao = Arrays.stream(colisaoPorPosicao).filter(c -> c > 0).average().orElse(0);

        // Exibição dos resultados
        System.out.println("---- Estatísticas de Colisões ----");
        System.out.println("Número de registros tentados: " + tentativasInsercao);
        System.out.println("Total de colisões: " + totalColisoes);
        System.out.printf("Percentual de colisões: %.2f%%\n", percentualColisoes);
        System.out.printf("Número médio de colisões por posição: %.2f\n", mediaColisoesPorPosicao);
    }
 // Hash1: Utiliza um vetor de pesos com números primos
    private int hash1(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            int peso = (i < PRIMOS.length) ? PRIMOS[i] : 1; // Usa os pesos primos ou 1 como fallback
            soma += Math.abs(chave.charAt(i)) * peso;
        }
        return soma % MAXTAB;
    }

    // Hash2: Alterna a soma e a subtração dos valores numéricos dos caracteres
    private int hash2(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            if (i % 2 == 0) {
                soma += Math.abs(chave.charAt(i)) * (i + 1);
            } else {
                soma -= Math.abs(chave.charAt(i)) * (i + 1);
            }
        }
        return Math.abs(soma) % MAXTAB;
    }

    // Hash3: Eleva os valores ao quadrado para criar maior variação nos valores
    private int hash3(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += Math.pow(Math.abs(chave.charAt(i)), 2) * (i + 1);
        }
        return soma % MAXTAB;
    }

    // Hash4: Combina a soma ponderada com uma multiplicação modular
    private int hash4(String chave) {
        int soma = 0;
        int multiplicador = 31; // Número primo usado como multiplicador
        for (int i = 0; i < chave.length(); i++) {
            soma = (soma * multiplicador + Math.abs(chave.charAt(i))) % MAXTAB;
        }
        return soma;
    }
 // hash5: processa a string de 4 em 4 bytes
 private int hash5(String chave) {
	 long sum = 0, mul = 1;
	  for (int i = 0; i < chave.length(); i++) {
	    mul = (i % 4 == 0) ? 1 : mul * 256;
	    sum += chave.charAt(i) * mul;
	  }
	  return (int)(Math.abs(sum) % MAXTAB);
	}
 /**
  * Implementação do algoritmo de hash sdbm.
  * Este algoritmo é conhecido por embaralhar bem os bits, oferecendo uma boa distribuição das chaves
  * e reduzindo colisões, especialmente em tabelas hash.
  *
  * @param chave A string cuja chave hash será calculada.
  * @return O índice hash resultante no intervalo [0, MAXTAB-1].
  */
 private int hash6(String chave) {
     long hash = 0; // Variável para acumular o valor hash. Usamos long para evitar overflow.

     // Iteramos sobre cada caractere da string
     for (int i = 0; i < chave.length(); i++) {
         char c = chave.charAt(i); // Obtemos o caractere na posição atual.

         /**
          * Atualização do valor hash:
          * hash = c + (hash << 6) + (hash << 16) - hash;
          * 
          * 1. `hash << 6` (hash deslocado 6 bits para a esquerda): 
          *    Multiplica o valor atual de hash por 64, aumentando sua magnitude.
          * 2. `hash << 16` (hash deslocado 16 bits para a esquerda): 
          *    Multiplica o valor atual de hash por 65.536, introduzindo uma variação maior.
          * 3. Somamos o valor do caractere `c` ao hash acumulado para incorporar o caractere atual.
          * 4. Subtraímos o valor original de `hash` para embaralhar os bits.
          *
          * Essa combinação de operações soma, deslocamento e subtração foi projetada para produzir 
          * uma dispersão uniforme das chaves, reduzindo a probabilidade de colisões.
          */
         hash = c + (hash << 6) + (hash << 16) - hash;
     }

     /**
      * Como o valor de hash pode ser negativo ou maior que o tamanho da tabela,
      * aplicamos o módulo `MAXTAB` e usamos `Math.abs` para garantir que o índice seja positivo.
      */
     return (int) (Math.abs(hash) % MAXTAB);
 }
    // Método principal para teste do método `testaFuncaoHash`
    public static void main(String[] args) {
        TabHashClientes tabela = new TabHashClientes();
        tabela.testaFuncaoHash(1000); // Testa a função hash com 10.000 registros
    }
}
