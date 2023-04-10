package pl.edu.pw.ee;

import org.junit.Test;

public class FileEncoderTest {
    
    public FileEncoderTest() {
    }

    @Test
    public void testEncode() throws Exception {
        FileEncoder fileEncoder = new FileEncoder("src/test/resources/decoded/test.txt", "src/test/resources/decoded/test_tree.bin", "src/test/resources/decoded/test_encoded.bin");
        fileEncoder.encode();
    }
    
}
