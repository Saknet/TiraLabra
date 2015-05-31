
package LZW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import datastructures.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZWDecompressionTest {
    LZWCompression LZWC = new LZWCompression();
    LZWDecompression LZWD = new LZWDecompression();
    
    public LZWDecompressionTest() {
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
    public void initializeDictionaryTest() {
        HashMap<Integer, String> dictionary = LZWD.initializeDictionary();
        String zero = dictionary.get(0);
        assertEquals("" + (char)0, zero);
        String ninetynine = dictionary.get(99);
        assertEquals("c", ninetynine);
        assertNull(dictionary.get(999));      
    }
    
    @Test
    public void createOutputTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = LZWD.createOutput("testfiles/lzwfilecreatetest.txt.LZW");
        String s = "jeesus ei pelasta";
        fos.write(s.getBytes());
        fos.close();
        FileInputStream fis = new FileInputStream("testfiles/lzwfilecreatetest.txt.d");
        s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        
        Assert.assertEquals("jeesus ei pelasta", s);
    }
    
    @Test
    public void writeFileTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("testfiles/lzwdwritefiletest.txt");
        LZWD.writeFile("jeesus", fos);
        
        FileInputStream fis = new FileInputStream("testfiles/lzwdwritefiletest.txt");
        String s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        
        Assert.assertEquals("jeesus", s);
    }
    
    //testaa kaiken!
    @Test
    public void runTest() throws IOException {
        FileInputStream fisa = new FileInputStream("testfiles/lzwfulltest.txt");
        String a = "";
        while (fisa.available() > 0) {
            char c = (char) fisa.read();
            a = a + c;
        }
        fisa.close();
        
        LZWC.run("testfiles/lzwfulltest.txt");
        LZWD.run("testfiles/lzwfulltest.txt.LZW");
        
        FileInputStream fisb = new FileInputStream("testfiles/lzwfulltest.txt.d");
        String b = "";
        while (fisb.available() > 0) {
            char c = (char) fisb.read();
            b = b + c;
        }
        fisb.close();
        
        Assert.assertEquals(a, b);
    }      
    
    @Test
    public void readFileTest() throws IOException {
        LZWC.run("testfiles/hikikomori.txt");
        ArrayList<Integer> codes = LZWD.readFile(new FileInputStream("testfiles/hikikomori.txt.LZW"));
        int a = codes.get(0);
        Assert.assertEquals((int) 'H', a);
        int b = codes.get(3);
        Assert.assertEquals(257, b);
        int c = codes.get(9);
        Assert.assertEquals((int) ' ', c);       
    }
    
    @Test
    public void decompressTest() throws IOException {
        LZWC.run("testfiles/hikikomori.txt");
        FileInputStream fis = new FileInputStream("testfiles/hikikomori.txt");
        int count = 0;
        
        while (fis.available() > 0) {
            char c = (char) fis.read();
            count++;
        }
        fis.close();
        
        FileOutputStream fos = LZWD.createOutput("testfiles/hikikomori.txt.LZW");
        LZWD.decompress(fos, LZWD.initializeDictionary(), LZWD.readFile(new FileInputStream("testfiles/hikikomori.txt.LZW")));
        
        FileInputStream fisb = new FileInputStream("testfiles/hikikomori.txt.d");
        int countb = 0;
        
        while (fisb.available() > 0) {
            char c = (char) fisb.read();
            countb++;
        }
        fisb.close();
        
        Assert.assertEquals(countb, count);
    }

}
