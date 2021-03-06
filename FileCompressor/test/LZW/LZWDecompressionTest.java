
package LZW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import datastructures.ArrayList;
import datastructures.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("testfiles/lzwdwritefiletest.txt")));
        LZWD.writeFile("jeesus", dos);
        dos.close();
        
        DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/lzwdwritefiletest.txt")));
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
        ArrayList<Integer> codes = LZWD.readFile(new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/hikikomori.txt.LZW"))));
        int a = codes.get(0);
        Assert.assertEquals((int) 'H', a);
        int b = codes.get(3);
        Assert.assertEquals(257, b);
        int c = codes.get(9);
        Assert.assertEquals((int) ' ', c);       
    }
    
    @Test
    public void decompressTest() throws IOException {
        FileInputStream fis = new FileInputStream("testfiles/hikikomori.txt");
        int count = 0;
        
        String a = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            a = a + c;
            count++;
        }
        fis.close();
        LZWC.run("testfiles/hikikomori.txt");
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(LZWD.createOutput("testfiles/hikikomori.txt.LZW")));
        LZWD.decompress(dos, LZWD.initializeDictionary(), LZWD.readFile(new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/hikikomori.txt.LZW")))));
        
        FileInputStream fisb = new FileInputStream("testfiles/hikikomori.txt.d");
        int countb = 0;
        
        String b = "";
        while (fisb.available() > 0) {
            char c = (char) fisb.read();
            b = b + c;
            countb++;
        }
        fisb.close();
        
        Assert.assertEquals(countb, count);
        Assert.assertEquals(a, b);
    }

}
