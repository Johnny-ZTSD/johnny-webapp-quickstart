package cn.johnnyzen.common.util;

import cn.johnnyzen.common.util.debug.Print;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/9/12  10:47:30
 * @description: ...
 */

public class CollectionUtilTest {
    @Test
    public void filterByKeyTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("ABC", "34534554");
        map.put("FBFDE%#SASDA", 324234);
        map.put("AGFRDFABC", true);

        Print.println(map);

        Map<String, Object> resultMap = CollectionUtil.filterByKey(map, "ABC");
        Print.println(resultMap);
    }

    @Test
    public void sortByMapKeyTest(){
        List<Map<String, Object>> listMap = new LinkedList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("signalName", "abCar");
        listMap.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("signalName", "abcbr");
        listMap.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("signalName", "abTbr");
        listMap.add(map3);

        Print.print(listMap);

        CollectionUtil.sortByMapKey(listMap, "signalName");
        Print.print(listMap);
    }
}
