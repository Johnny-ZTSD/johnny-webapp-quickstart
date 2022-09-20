package cn.johnnyzen.common.util.debug;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  02:28:37
 * @description: ...
 */

public class PrintTest {

    @Test
    public void testPrint() {
        Print.print("hello");
        Print.println();
        Print.println("world~");

        Assert.assertEquals(true, "success".length()>0);
    }

    @Test
    public void testPrintMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        map.put("abb", 32535);
        Print.println(map);
    }
}
