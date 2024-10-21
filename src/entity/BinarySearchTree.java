package entity;

public interface BinarySearchTree {
    void insert(int key);
    boolean search(int key);
    void delete(int key);
    void inorderTraversal();
}