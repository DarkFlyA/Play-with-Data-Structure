package BinarySearchTree;

import java.util.*;

public class BST<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left;
        public Node right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e) {
        root = add(root, e);
    }

    // 递归
    // 向以node为根的二分搜索树中插入元素E
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, E e) {
        if(node == null) {
            size++;
            return new Node(e);
        }

        if(e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if(e.compareTo(node.e) > 0)
            node.right = add(node.right, e);

        return node;
    }

    // 非递归
    // 向以node为根的二分搜索树中插入元素E
    private void Add(Node node, E e) {
        if(node == null){
            size++;
            root = new Node(e);
            return;
        }

        Node p = node;
        while(true){
            if(e.equals(p.e))
                return;
            else if(e.compareTo(p.e) < 0){
                if (p.left == null) {
                    size++;
                    p.left = new Node(e);
                    return;
                }
                p = p.left;
            }
            else if(e.compareTo(p.e) > 0) {
                if (p.right == null) {
                    size++;
                    p.right = new Node(e);
                    return;
                }
                p = p.right;
            }
        }
    }

    // 查看二分搜索树中是否包含元素e
    public boolean contains(E e) {
        return contains(root, e); // 递归
//        return Contains(root, e); // 非递归
    }

    // 递归
    // 查看以node为根的二分搜索树中是否包含元素e
    private boolean contains(Node node, E e) {
        if(node == null)
            return false;

        if(e.compareTo(node.e) == 0)
            return true;
        else if(e.compareTo(node.e) < 0)
            return contains(node.left, e);
        else
            return contains(node.right, e);
    }

    // 非递归
    // 查看以node为根的二分搜索树中是否包含元素e
    private boolean Contains(Node node, E e) {
        if(node == null)
            return false;

        Node p = node;
        while (p != null) {
            if(e.compareTo(p.e) == 0)
                return true;
            else if(e.compareTo(p.e) < 0)
                p = p.left;
            else
                p = p.right;
        }
        return false;
    }

    // 二分搜索树的前序遍历
    public void preOrder() {
        preOrder(root);  //递归遍历
//        PreOrder(root);  // 非递归遍历
    }

    // 递归：前序遍历以node为根的二分搜索树
    private void preOrder(Node node) {
        if(node == null)
            return;

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    // 非递归：前序遍历以node为根的二分搜索树
    // 先将根结点压栈，在栈不为空的时候执行循环：
    // 1、让栈顶元素p出栈，访问栈顶元素p
    // 2、如果p的右孩子不为空，则让其右孩子先进栈
    // 3、如果p的左孩子不为空，则再让其左孩子进栈
    private void PreOrder(Node node) {
        Stack<Node> s = new Stack<>();
        s.push(node);

        Node p;
        while (!s.empty()) {
            p = s.pop();
            System.out.println(p.e);
            if(p.right != null)
                s.push(p.right);
            if(p.left != null)
                s.push(p.left);
        }
    }

    // 二分搜索树的中序遍历
    public void inOrder() {
        inOrder(root);   // 递归
//        InOrder(root);   // 非递归
    }

    // 递归：中序遍历以node为根的二分搜索树
    private void inOrder(Node node) {
        if(node == null)
            return;

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    // 非递归：中序遍历以node为根的二分搜索树
    private void InOrder(Node node) {
        Stack<Node> s = new Stack<>();

        Node p = node;
        while (p != null || !s.empty()) {
            while (p != null) {
                s.push(p);
                p = p.left;
            }
            if(!s.empty()) {
                p = s.pop();
                System.out.println(p.e);
                p = p.right;
            }
        }
    }

    // 二分搜索树的后序遍历
    public void postOrder() {
        postOrder(root);   // 递归
        PostOrder(root);   // 非递归
    }

    // 递归：后序遍历以node为根的二分搜索树
    private void postOrder(Node node) {
        if(node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    // 非递归：后序遍历以node为根的二分搜索树
    // 对于任一结点P，先将其入栈。
    // 如果P不存在左孩子和右孩子，则可以直接访问它；
    // 或者P存在左孩子或者右孩子，但是其左孩子和右孩子都已被访问过了，则同样可以直接访问该结点。
    // 若非上述两种情况，则将P的右孩子和左孩子依次入栈。
    private void PostOrder(Node node) {
        Stack<Node> s = new Stack<>();
        Node p;
        Node pre = null;
        s.push(node);

        while (!s.empty()) {
            p = s.peek();
            if( (p.left == null && p.right == null ) ||
                    (pre != null && (pre == p.left || pre == p.right)))
            {
                System.out.println(p.e);
                s.pop();
                pre = p;
            }
            else {
                if(p.right != null)
                    s.push(p.right);
                if(p.left != null)
                    s.push(p.left);
            }
        }
    }

    // 模拟系统栈调用实现非递归算法
    private class Command {
        private boolean visit;
        private Node node;

        public Command(boolean visit, Node node) {
            this.visit = visit;
            this.node = node;
        }
    }

    public ArrayList<E> stackOrder() {
        return stackOrder(root);
    }

    private ArrayList<E> stackOrder(Node node) {
        ArrayList<E> res = new ArrayList<>();
        if(node == null)
            return res;

        Stack<Command> stack = new Stack<>();
        stack.push(new Command(false, node));
        while (!stack.empty()) {

            Command command = stack.pop();
            if(command.visit)
                res.add(command.node.e);
            else {
                assert command.visit == false;
//                stack.push(new Command(true, command.node));  // 后序遍历
                if(command.node.right != null)  // 右孩子入栈
                    stack.push(new Command(false, command.node.right));
                stack.push(new Command(true, command.node));  // 中序遍历
                if(command.node.left != null)   // 左孩子入栈
                    stack.push(new Command(false, command.node.left));
//                stack.push(new Command(true, command.node));  // 前序遍历
            }

        }
        return res;
    }


    // 二分搜索树层序遍历
    public void levelOrder() {

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            System.out.println(cur.e);
            if(cur.left != null) {
                queue.add(cur.left);
            }
            if(cur.right != null) {
                queue.add(cur.right);
            }
        }

    }

    // 寻找二分搜索树的最小元素
    public E minimum() {
        if(size == 0)
            throw new IllegalArgumentException("BST is empty");

        return minimum(root).e;
    }

    // 递归：返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    // 寻找二分搜索树的最大元素
    public E maximum() {
        if(size == 0)
            throw new IllegalArgumentException("BST is empty");

        return maximum(root).e;
    }

    // 递归：返回以node为根的二分搜索树的最大值所在的节点
    private Node maximum(Node node) {
        if (node.right == null)
            return node;
        return minimum(node.right);
    }

    // 二分搜索树中删除最小值所在节点，返回最小值
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    // 删除以node为根的二分搜索树的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 二分搜索树中删除最大值所在节点，返回最大值
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    // 删除以node为根的二分搜索树的最大节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node) {

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }


    // 从二分搜索树中删除元素为e的节点
    public void remove(E e) {
        root = remove(root, e);
    }

    // 删除以node为根的二分搜索树中值为e的节点
    // 返回删除节点后新的二分搜索树的根
    private Node remove(Node node, E e) {

        if(node == null) // 没找到值为e的节点
            return null;

        if(e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        }
        else if(e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        }
        else { // node.e == e

            // 待删除节点左子树为空
            if(node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除节点右子树为空
            if(node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况
            // 找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.left = node.left;
            successor.right = removeMin(node.right);

            node.left = node.right = null;

            return successor;
        }
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] numbers = {5,3,6,8,4,2};
        for(int num: numbers)
            bst.add(num);

//        System.out.println(bst.contains(1));

//        bst.preOrder();
//        System.out.println();

//        bst.remove(6);

//        bst.inOrder();
//        System.out.println();

//        bst.postOrder();
//        System.out.println();

//        ArrayList<Integer> res = bst.stackOrder();
//        ListIterator<Integer> iterator = res.listIterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
    }
}
