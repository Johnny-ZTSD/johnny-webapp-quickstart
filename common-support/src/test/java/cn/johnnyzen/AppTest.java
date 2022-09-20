package cn.johnnyzen;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static void t(){
        String originalFilename = "ssdsfdssdfsdf.xlsx";
        if(!(originalFilename.endsWith(".zip") || originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx")) ) {
            System.out.println("world");
            return;
        }
        System.out.println("hello");
    }

    @Test
    public void test(){
        t();
    }
}
