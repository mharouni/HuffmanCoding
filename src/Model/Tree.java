package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Tree {

    public HashMap<Character, String> map = new HashMap<>();
    public Node root;
    public PriorityQueue<Node> nodes;

    public Tree(PriorityQueue<Node> nodes) {
        this.nodes = nodes;
    }


    public void createTree() {

        while (this.nodes.size() > 1) {
            Node n = this.nodes.poll();
            Node n2 = this.nodes.poll();
            Node init = new Node('*', n.getFreq() + n2.getFreq());
            init.setLeft(n);
            init.setRight(n2);
            this.nodes.add(init);

        }
        this.root = this.nodes.peek();
    }


    public void test(Node node) {
        if (node.getLeft() != null)
            test(node.getLeft());
        if (node.getRight() != null)
            test(node.getRight());
        System.out.println(node.toString());

    }

    public void generateCode() {
        String temp = "";
        this.traverse(this.root, temp, this.map);

    }


    public void traverse(Node node,String temp, HashMap map) {
       if (node  == null)
            return;
        if ( node.getRight() == null && node.getLeft() == null)
        {
            map.put(node.getCharacter(),temp);
           // System.out.println("ayaaa");
        }
        this.traverse(node.getRight(), temp + "1", map);
        this.traverse(node.getLeft(), temp + "0", map);
    }




}