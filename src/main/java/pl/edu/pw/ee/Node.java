package pl.edu.pw.ee;

public class Node implements Comparable<Node> {

    private byte character;
    private int frequency;
    private Node left;
    private Node right;
    private boolean isLeaf;

    public Node(byte character, int frequency) {
        if (character < 0 || character >= 256) {
            throw new IllegalArgumentException("Character must be in ASCII code");
        }
        if (frequency <= 0) {
            throw new IllegalArgumentException("Frequency must be positive");
        }
        this.frequency = frequency;
        this.character = character;
        left = null;
        right = null;
        isLeaf = true;
    }

    public Node(Node left, Node right, int frequency) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (frequency <= 0) {
            throw new IllegalArgumentException("Frequency must be positive");
        }
        this.left = left;
        this.right = right;
        this.frequency = frequency;
        isLeaf = false;
    }

    public Node(Node left, Node right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        this.left = left;
        this.right = right;
        character = -1;
        frequency = -1;
        isLeaf = false;
    }
    
    public Node(byte character) {
        if (character < 0 || character >= 128) {
            throw new IllegalArgumentException("Character must be in ASCII code");
        }
        left = null;
        right = null;
        this.character = character;
        frequency = -1;
        isLeaf = true;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public int compareTo(Node otherNode) {
        if (otherNode == null) {
            throw new IllegalArgumentException("Cannot compare Node to null!");
        }
        return Integer.compare(this.frequency, otherNode.frequency);
    }

    public int getFrequency() {
        return frequency;
    }

    public byte getCharacter() {
        return character;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
    
    
    

}
