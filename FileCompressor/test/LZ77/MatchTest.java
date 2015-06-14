
package LZ77;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatchTest {
    
    public MatchTest() {
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
    public void createMatchTest() {
        Match m = new Match();
        Assert.assertEquals(-1, m.getDistance());
        Assert.assertEquals(-1, m.getLength());
        Assert.assertEquals(-1, m.getStartPointer());
    }
    
    @Test
    public void setterTest() {
        Match m = new Match();
        m.setDistance(0);
        m.setLength(4);
        m.setStartPointer(-99);
        Assert.assertEquals(0, m.getDistance());
        Assert.assertEquals(4, m.getLength());
        Assert.assertEquals(-99, m.getStartPointer());
    }
}
