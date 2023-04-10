package pl.edu.pw.ee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileDecoder {

    private String decodedFilePath;
    private String encodedtreePath;
    private String encodedFilePath;

    public FileDecoder(String decodedFilePath, String encodedtreePath, String encodedFilePath) {
        this.decodedFilePath = decodedFilePath;
        this.encodedtreePath = encodedtreePath;
        this.encodedFilePath = encodedFilePath;
    }

    public void decode() throws IOException {
        File encodedTreeFile = new File(encodedtreePath);
        byte[] encodedTreeBytes = Files.readAllBytes(encodedTreeFile.toPath());

        HuffmanTree huffmanTree = new HuffmanTree(encodedTreeBytes);

        File encodedFile = new File(encodedFilePath);
        byte[] encodedBytes = Files.readAllBytes(encodedFile.toPath());

        Node node = huffmanTree.getRoot();
        BitReader bitReader = new BitReader(encodedBytes);

        ByteArrayBuilder decodedByteArrayBuilder = new ByteArrayBuilder();

        while (bitReader.hasNextBit()) {
            if (node.isLeaf()) {
                decodedByteArrayBuilder.appendByte(node.getCharacter());
            } else {
                int readBit = bitReader.nextBit();
                if (readBit == 0) {
                    node = node.getLeft();
                } else {
                    node = node.getRight();
                }
            }
        }
        
        File decodedFile = new File(decodedFilePath);
        Files.write(decodedFile.toPath(), decodedByteArrayBuilder.getBuiltBytes());
        // TODO
        // WYPISYWANIE ODKODOWANEGO DO PLIKU
    }

}
