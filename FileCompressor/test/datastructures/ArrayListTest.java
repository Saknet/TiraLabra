
package datastructures;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayListTest {
    ArrayList<Integer> list = new ArrayList<>();
    
    public ArrayListTest() {
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
    public void addAndGetTest() {
        list.add(-3);
        int a = list.get(0);
        Assert.assertEquals(-3, a);
        list.add(345);
        int b = list.get(1);
        Assert.assertEquals(345, b);
        list.add(44);
        int size = list.size();
        Assert.assertEquals(3, size);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void IndexOutOfBoundsExceptionTest() {
        ArrayList<String> emptyList = new ArrayList();
        String s = emptyList.get(0);
        emptyList.checkRange(-1);
        emptyList.checkRange(0);
        emptyList.checkRange(1);
    }
    
    @Test
    public void initListTest() {
        ArrayList<String> list = new ArrayList();
        list.init("1");
        Assert.assertEquals("1", list.get(0));
        Assert.assertEquals(1, list.size());
    }
}
