
package LZW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZWDecompressionTest {
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
    
    @Test
    public void decompressTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("testfiles/lzwdecompresstest.txt.LZW");
        HashMap<Integer, String> dictionary = LZWD.initializeDictionary();
        ArrayList<Integer> codes = new ArrayList<>();
        codes.add(2);
        LZWD.decompress(fos, dictionary, codes);
        FileInputStream fis = new FileInputStream("testfiles/lzwdecompresstest.txt.LZW");
        String s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        Assert.assertEquals("", s);
    }
}
