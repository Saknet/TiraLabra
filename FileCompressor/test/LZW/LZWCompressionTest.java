
package LZW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import datastructures.ArrayList;
import datastructures.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZWCompressionTest {
    LZWCompression LZWC = new LZWCompression();
    
    public LZWCompressionTest() {
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
        HashMap<String, Integer> dictionary = LZWC.initializeDictionary();
        int a = dictionary.get("a");
        assertEquals(97, a);
        int test = dictionary.get("{");
        assertEquals(123, test);
        assertNull(dictionary.get("33"));
    }
    
    @Test
    public void createOutputTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = LZWC.createOutput("testfiles/lzwfilecreatetest.txt");
        String s = "jeesus pelastaa";
        fos.write(s.getBytes());
        fos.close();
        FileInputStream fis = new FileInputStream("testfiles/lzwfilecreatetest.txt.LZW");
        s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        
        Assert.assertEquals("jeesus pelastaa", s);
    }
    
   
    @Test
    public void compressEmptyTest() throws IOException {
        HashMap<String, Integer> dictionary = LZWC.initializeDictionary();
        ArrayList<Integer> codes = LZWC.compress(dictionary, "testfiles/empty.txt");
        Assert.assertEquals(0, codes.size());       
    }
    
    @Test
    public void writeFileTest() throws IOException {
        HashMap<String, Integer> dictionary = LZWC.initializeDictionary();
        ArrayList<Integer> codes = LZWC.compress(dictionary, "testfiles/lzworiginalfile.txt"); 
        LZWC.writeFile(codes, "testfiles/lzworiginalfile.txt");
        
        boolean dollar = false;
        FileInputStream fis = new FileInputStream("testfiles/lzworiginalfile.txt.LZW");
        while (fis.available() > 0) {
            if ((char) fis.read() == '$') {
                dollar = true;
            }
        }
        fis.close();
        Assert.assertTrue(dollar);
    }
    
    @Test
    public void runTest() throws IOException {
        LZWC.run("testfiles/lzworiginalfile.txt");
        boolean dollar = false;
        FileInputStream fis = new FileInputStream("testfiles/lzworiginalfile.txt.LZW");
        while (fis.available() > 0) {
            if ((char) fis.read() == '$') {
                dollar = true;
            }
        }
        fis.close();
        Assert.assertTrue(dollar);
    }
    
    
    @Test
    public void compressTest() throws IOException {
        ArrayList<Integer> codes = LZWC.compress(LZWC.initializeDictionary(),"testfiles/hikikomori.txt");
        Assert.assertEquals(3842, codes.size());
        int a = codes.get(0);
        Assert.assertEquals((int) 'H', a);
        int b = codes.get(3);
        Assert.assertEquals(257, b);
        int c = codes.get(9);
        Assert.assertEquals((int) ' ', c);
        
    }
}

