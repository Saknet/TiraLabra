
package IO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryIOTest {
    
    public BinaryIOTest() {
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
    public void writingTest() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream("testfiles/testoutput.txt");
            BinaryOutput bo = new BinaryOutput(fos);
            bo.clearBuffer();           
            bo.write(2132332423, 32);
            bo.write(123, 12);
            bo.write(0, 2);
            bo.writeByte(120);
            bo.writeBit(true);
            bo.writeBit(false);
            bo.close();
        } catch (Exception e) {
        }
        
        FileInputStream fis = new FileInputStream("testfiles/testoutput.txt");
        BinaryInput bi = new BinaryInput(fis);
        int a = bi.readInt(32);
        Assert.assertEquals(2132332423, a);
        int b = bi.readInt(12);
        Assert.assertEquals(123, b);
        int c = bi.readInt(2);
        Assert.assertEquals(0, c);
        char x = bi.readChar();
        Assert.assertEquals('x', x);
        boolean bit = bi.readBit();
        Assert.assertEquals(true, bit);
        bit = bi.readBit();
        Assert.assertEquals(false, bit);
    }
}