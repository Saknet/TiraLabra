
package datastructures;

import huffmancoding.HuffmanCompression;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashMapTest {
    HuffmanCompression hC = new HuffmanCompression();
    
    public HashMapTest() {
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
    public void randomFileTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/hashmaptestifile.txt");
        int s = freq.get('s');
        org.junit.Assert.assertEquals(267, s);
        org.junit.Assert.assertNull(freq.get('Å'));     
    }
}
