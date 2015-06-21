
package LZ77;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZ77DecompressionTest {
    LZ77Compression lz77c = new LZ77Compression();
    LZ77Decompression lz77d = new LZ77Decompression();
    
    public LZ77DecompressionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void runTest() throws IOException {
        DataInputStream disa = new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/lz77full.txt")));
        String a = "";
        while (disa.available() > 0) {
            char c = (char) disa.read();
            a = a + c;
        }
        disa.close();
        
        lz77c.run("testfiles/lz77full.txt");
        lz77d.run("testfiles/lz77full.txt.LZ77");
        
        DataInputStream disb = new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/lz77full.txt.7")));
        String b = "";
        while (disb.available() > 0) {
            char c = (char) disb.read();
            b = b + c;
        }
        disb.close();
        
        Assert.assertEquals(a, b);
    }
}
