package AVLTree;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> {

    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right =  null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.equals(node.key)) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else { // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
        }
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        node.value = newValue;
    }

    // 判断该二叉树是否是二分搜索树
    public boolean isBST() {

        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if(keys.get(i-1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }

    // 中序遍历
    private void inOrder(Node node, ArrayList<K> keys) {
        if(node == null) {
            return;
        }

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    // 判断该二叉树是否是一棵平衡二叉树
    public boolean isBalanced() {
        return isBalanced(root);
    }

    // 判断以node为根的二叉树是否是平衡二叉树， 递归算法
    private boolean isBalanced(Node node) {

        if(node == null) {
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1) {
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    // 获得节点node的高度
    private int getHeight(Node node) {
        if(node == null) {
            return 0;
        }
        return node.height;
    }

    // 获得节点node的平衡因子
    private int getBalanceFactor(Node node){
        if(node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {

        if(node == null) {
            size++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        }
        else if(key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        }
        else {
            // key.compareTo(node.key) == 0, 更新节点的value
            node.value = value;
        }

        // 更新node的height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        return rotate(node);
    }


    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y) {

        Node x = y.left;
        Node T3 = x.right;

        // 向右旋转
        x.right = y;
        y.left = T3;

        // 更新节点的height
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        // 向左旋转
        x.left = y;
        y.right = T2;

        // 更新节点的height
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }


    // 返回以node为根的二叉树的最小节点，即最左节点
    private Node minimum(Node node) {
        if(node.left == null){
            return node;
        }
        return minimum(node.left);
    }

    // 从二分搜索树中删除键为key的节点
    // 节点删除成功则返回节点的value，否则返回null
    public V remove(K key) {
        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    // 从以node为根的平衡二叉树中删除键为key的节点
    private Node remove(Node node, K key) {

        if (node == null) {
            return null;
        }

        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else { // key.compareTo(node.key) == 0

            // 待删除节点左子树为null
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }

            // 待删除节点右子树为null
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            }

            // 待删除节点的左右子树均不为空
            // 找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
            // 用该节点顶替待删除节点的位置
            else {
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;
                retNode = successor;
            }
        }

        if (retNode == null){
            return null;
        }

        // 更新node的height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        return rotate(retNode);
    }

    // 添加、删除之后,判断node的平衡因子，决定是否进行旋转
    private Node rotate(Node node) {
        // 获取节点平衡因子，判断是否需要
        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1) {
            // 平衡因子绝对值大于1，此时以node为根的AVLTree不平衡，需要调整

            // 1.LL右旋：add插入的元素在不平衡的节点的左侧的左侧，树向左倾斜
            if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }

            // 2.RR左旋：add插入的元素在不平衡的节点的右侧的右侧，树向右倾斜
            if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }

            // 3.LR(先左旋，再右旋)：add插入的元素在不平衡的节点的左侧的右侧
            // 先 leftRotate(node.left),转换为LL
            // 再 rightRotate(node)
            if(balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // 4.RL(先右旋，再左旋)：add插入的元素在不平衡的节点的右侧的左侧
            // 先 rightRotate(node.right),转换为RR
            // 再 leftRotate(node)
            if(balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }
}
