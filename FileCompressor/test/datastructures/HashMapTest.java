
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
    
    @Test
    public void putAndGetTest() {
        HashMap<Integer, String> testMap = new HashMap<>();
        testMap.put(2, "jeeeee");
        testMap.put(2, "jee");
        Assert.assertEquals("jee", testMap.get(2));
        testMap.put(3, "abc");
        Assert.assertEquals("abc", testMap.get(3));
        Assert.assertNull(testMap.get(1));
    }
    
    @Test
    public void containsKeyTest() {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("as", "jeeeee");
        testMap.put("as", "jee");
        Assert.assertTrue(testMap.containsKey("as"));
        testMap.put("qw", "abc");   
        Assert.assertTrue(testMap.containsKey("qw"));
        Assert.assertFalse(testMap.containsKey("zx"));
    }
    
    @Test
    public void keySetTest() {
        HashMap<Character, String> testMap = new HashMap<>();
        testMap.put('a', "01");
        testMap.put('b', "10");
        testMap.put('c', "11");
        ArrayList<Character> keys = testMap.keySet();
        Assert.assertEquals(3, keys.size());
        char a = keys.get(0);
        char b = keys.get(1);
        char c = keys.get(2);
        Assert.assertEquals('a', a);
        Assert.assertEquals('b', b);
        Assert.assertEquals('c', c);
    }
}
