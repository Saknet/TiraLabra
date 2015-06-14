package LZ77;

import IO.BinaryInput;
import IO.BinaryOutput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZ77CompressionTest {
    LZ77Compression lz77c = new LZ77Compression();
    
    public LZ77CompressionTest() {
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
    public void writeBlock() throws FileNotFoundException, IOException {
        char[] buffer = new char[8];
        buffer[0] = 'a';
        buffer[1] = 'b';
        buffer[2] = 'c';
        buffer[3] = 'd';
        buffer[4] = 'e';
        buffer[5] = 'f';
        buffer[6] = 'g';
        buffer[7] = 'h';
        Match m = new Match();
        m.setDistance(2);
        m.setLength(3);
        m.setStartPointer(3);
        FileOutputStream fos = new FileOutputStream("testfiles/lz77writeblock.txt");
        BinaryOutput bo = new BinaryOutput(fos);
        lz77c.writeBlock(buffer, m, bo);
        fos.close();
        FileInputStream fis = new FileInputStream("testfiles/lz77writeblock.txt");
        BinaryInput bi = new BinaryInput(fis);
        String s = "";
        while (fis.available() > 0) {
            char c = bi.readChar();
            if (c == '$') {
                s = s + c;
                int distance = bi.readInt(8);
                s = s + distance;
                int length = bi.readInt(8);    
                s = s + length;
            } else {
                s = s + c;
            }
        }
        String a = "abc$23g";
        
        Assert.assertEquals(a, s);        
    }
}
