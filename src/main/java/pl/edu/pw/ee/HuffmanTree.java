package pl.edu.pw.ee;

import java.util.PriorityQueue;

public class HuffmanTree {

    private Node root = null;

    private String[] huffmanCodes = new String[256];

    public HuffmanTree(int[] counter) {
        validateCounter(counter);
        root = buildTree(counter);
        buildHuffmanCodes(root, "");
    }

    private void validateCounter(int[] counter) {
        if (counter.length != 256) {
            throw new IllegalArgumentException("Only 1 byte character counter is supported, size of array has to be 256");
        }
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] < 0) {
                throw new IllegalArgumentException("Values in counter array cannot be negative");
            }
        }
    }

    private Node buildTree(int[] counter) {
        PriorityQueue<Node> nodes = fillNodesFromCounter(counter);

        int sumOfFrequencies;
        while (nodes.size() > 1) {
            Node left = nodes.remove();
            Node right = nodes.remove();
            sumOfFrequencies = left.getFrequency() + right.getFrequency();
            Node parentNode = new Node(left, right, sumOfFrequencies);
            nodes.add(parentNode);
        }

        return nodes.remove();
    }

    private PriorityQueue<Node> fillNodesFromCounter(int[] counter) {
        PriorityQueue<Node> nodes = new PriorityQueue<>();
        int frequency;
        for (int c = 0; c < counter.length; c++) {
            frequency = counter[c];
            if (frequency > 0) {
                byte character = (byte) c;
                Node node = new Node(character, frequency);
                nodes.add(node);
            }
        }
        return nodes;
    }

    public byte[] getTreeBinaryForm() {
        ByteArrayBuilder builder = new ByteArrayBuilder();
        encodeTree(root, builder);
        return builder.getBuiltBytes();
    }

    private void encodeTree(Node node, ByteArrayBuilder builder) {
        if (node.isLeaf()) {
            builder.append(true);
            builder.appendByte(node.getCharacter());
        } else {
            builder.append(true);
            encodeTree(node.getLeft(), builder);
            encodeTree(node.getRight(), builder);
        }
    }

    // ******************************************
    public HuffmanTree(byte[] binaryTreeForm) {
        root = buildTree(binaryTreeForm);
        //correctTree();
    }

    private Node buildTree(byte[] binaryTreeForm) {
        BitReader bitReader = new BitReader(binaryTreeForm);
        return decodeTree(bitReader);
    }

    private void buildHuffmanCodes(Node node, String result) {
        if (!node.isLeaf() && node.getLeft() != null && node.getRight() != null) {
            buildHuffmanCodes(node.getLeft(), result + '0');
            buildHuffmanCodes(node.getRight(), result + '1');
        } else {
            huffmanCodes[Byte.toUnsignedInt(node.getCharacter())] = result;
        }
    }

    private Node decodeTree(BitReader bitReader) {
        if (bitReader.hasNextBit()) {
            if (bitReader.nextBit() == 1) {
                return new Node((byte)bitReader.nextByte());
            } else {
                Node leftChild = decodeTree(bitReader);
                Node rightChild = decodeTree(bitReader);
                return new Node(leftChild, rightChild);
            }
        } else {
            throw new RuntimeException("Unexpected exception during decoding Tree");
        }
    }

    public String getCharacterCode(byte character) {
        return huffmanCodes[Byte.toUnsignedInt(character)];
    }

    public Node getRoot() {
        return root;
    }

}
