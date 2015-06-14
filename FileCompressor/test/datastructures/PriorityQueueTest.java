
package datastructures;

import huffmancoding.HuffmanCompression;
import huffmancoding.Node;
import huffmancoding.TreeBuilder;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PriorityQueueTest {
    PriorityQueue pq;
    TreeBuilder tb = new TreeBuilder();
    HuffmanCompression hC = new HuffmanCompression();
    
    public PriorityQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addAndPollTest() {
        Node node1 = new Node('a', 3);
        Node node2 = new Node('b', 2);
        PriorityQueue pq2 = new PriorityQueue();
        pq2.add(node1);
        pq2.add(node2);
        Assert.assertEquals(2, pq2.size());
        Node smallest = pq2.poll();
        Assert.assertEquals(2, smallest.getFrequency());
        Assert.assertEquals(1, pq2.size());
    }
    
    @Test
    public void filePollTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        pq = tb.addSymbolsToPQ(freq);
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
            pq.add(parent);
        }
        Node biggest = pq.poll();
        Assert.assertEquals(1329, biggest.getFrequency());
        Assert.assertEquals(0, pq.size());
    }
}
