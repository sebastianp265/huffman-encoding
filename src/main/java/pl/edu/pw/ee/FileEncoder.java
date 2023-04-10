package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

public class FileEncoder {

    private String orgFilePath;
    private String encodedTreePath;
    private String encodedFilePath;

    public FileEncoder(String orgFilePath, String encodedTreePath, String encodedFilePath) {
        this.orgFilePath = orgFilePath;
        this.encodedTreePath = encodedTreePath;
        this.encodedFilePath = encodedFilePath;
    }

    public void encode() throws IOException {
        File orgFile = new File(orgFilePath);
        byte[] orgFileBytes = Files.readAllBytes(orgFile.toPath());

        int[] counter = countCharacters(orgFileBytes);
        HuffmanTree huffmanTree = new HuffmanTree(counter);
        writeEncodedTree(huffmanTree);
        writeEncodedFile(orgFileBytes, huffmanTree);
    }

    private void writeEncodedTree(HuffmanTree huffmanTree) throws IOException {
        File encodedTreeFile = new File(encodedTreePath);

        Files.write(encodedTreeFile.toPath(), huffmanTree.getTreeBinaryForm());
    }

    private void writeEncodedFile(byte[] orgFileBytes, HuffmanTree huffmanTree) throws IOException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();

        for (byte character : orgFileBytes) {
            String binaryHuffmanCode = huffmanTree.getCharacterCode(character);
            int huffmanCodeLength = binaryHuffmanCode.length();
            for (int i = 0; i < huffmanCodeLength; i++) {
                if (binaryHuffmanCode.charAt(i) == '1') {
                    byteArrayBuilder.append(true);
                } else {
                    byteArrayBuilder.append(false);
                }
            }
        }
        byte[] encodedFileBytes = byteArrayBuilder.getBuiltBytes();

        File encodedFile = new File(encodedFilePath);
        Files.write(encodedFile.toPath(), encodedFileBytes);
    }

    private int[] countCharacters(byte[] bytes) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        int[] counter = new int[256];
        for (byte character : bytes) {
            counter[Byte.toUnsignedInt(character)]++;
        }
        return counter;
    }

}
