package Model;

import java.util.HashMap;
import java.util.Map;

public class Node implements Comparable<Node> {
    private Node left = null;
    private Node right = null;
    private char character;
    private int freq;




    public Node(char character, int freq) {
        this.character = character;
        this.freq = freq;
        this.left = null;
        this.right = null;

    }

    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public int getFreq() {
        return freq;
    }

    public char getCharacter() {
        return character;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) { this.right = right;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public String toString() {
        return this.character + "=" + this.freq ;
    }

    @Override
    public int compareTo(Node o) {
        if ( this.freq > o.freq)
            return 1;
        else if (this.freq < o.freq)
            return -1;
        else
            return 0;
    }


}
