package pl.edu.pw.ee;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileDecoderTest {
    
    public FileDecoderTest() {
    }

    @Test
    public void testDecode() throws Exception {
        FileDecoder fileDecoder = new FileDecoder("src/test/resources/encoded/test.txt", "src/test/resources/encoded/test_tree.bin", "src/test/resources/encoded/test_encoded.bin");
        fileDecoder.decode();
    }
    
}
