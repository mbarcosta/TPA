package entity;

public class BST implements BinarySearchTree {
    
    private class Node {
        int chave;
        Node left;
        Node right;

        Node(int chave) {
            this.chave = chave;
            left = null;
            right = null;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    @Override
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.chave) {
            root.left = insertRec(root.left, key);
        } else if (key > root.chave) {
            root.right = insertRec(root.right, key);
        }

        return root;
    }

    @Override
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (key == root.chave) {
            return true;
        }
        return key < root.chave ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    @Override
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.chave) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.chave) {
            root.right = deleteRec(root.right, key);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.chave = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.chave);
        }
        return root;
    }

    private int minValue(Node root) {
        int minValue = root.chave;
        while (root.left != null) {
            minValue = root.left.chave;
            root = root.left;
        }
        return minValue;
    }

    @Override
    public void inorderTraversal() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.chave + " ");
            inorderRec(root.right);
        }
    }

    // Método principal para testar a implementação
    public static void main(String[] args) {
        BST tree = new BST();

        // Inserindo valores na árvore
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        // Exibindo a árvore em ordem
        System.out.println("Árvore em ordem:");
        tree.inorderTraversal(); // Deve imprimir: 20 30 40 50 60 70 80

        // Buscando por um valor
        System.out.println("Buscar 40: " + tree.search(40)); // Deve retornar true
        System.out.println("Buscar 90: " + tree.search(90)); // Deve retornar false

        // Removendo um valor
        tree.delete(20);
        System.out.println("Árvore após remoção de 20:");
        tree.inorderTraversal(); // Deve imprimir: 30 40 50 60 70 80

        tree.delete(30);
        System.out.println("Árvore após remoção de 30:");
        tree.inorderTraversal(); // Deve imprimir: 40 50 60 70 80

        tree.delete(50);
        System.out.println("Árvore após remoção de 50:");
        tree.inorderTraversal(); // Deve imprimir: 40 60 70 80
    }
}

