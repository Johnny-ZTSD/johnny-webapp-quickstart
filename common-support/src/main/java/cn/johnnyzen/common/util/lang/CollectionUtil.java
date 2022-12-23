package cn.johnnyzen.common.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/9/12  09:45:16
 * @description: ...
 */

public class CollectionUtil {
    private final static Logger LOG = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 从 map 中模糊匹配 key 值
     * @param map
     * @param filterKey := keyword
     * @param <V>
     * @return
     * @reference-doc
     *  [1] Java从Map中模糊匹配key值 - CSDN - https://blog.csdn.net/zlfjavahome/article/details/124201519
     *  [2] 【java】从Map中模糊匹配key值 - 蒲公英云 - https://www.dandelioncloud.cn/article/details/1488733752569483266
     *  [3] Java模糊匹配map中的多个key值 - CSDN - https://blog.csdn.net/weixin_52967215/article/details/124602296
     */
    public static <V> Map<String, V> filterByKey(Map<String, V> map, String filterKey){
        Map<String, V> resultMap = new HashMap<>();
        map.entrySet().forEach(entry -> {
            if(entry.getKey().contains(filterKey)){
                resultMap.put(entry.getKey(), entry.getValue());
            }
        });
        return resultMap;
    }

    /**
     * List<Map<String, V>> : 根据 Map 的 某个固定的 key 排序
     * @description 不区分字母大小写的方式排序
     * @reference-doc
     *  [1] Java 8 之 Stream sorted() List排序并排名 - CSDN - https://blog.csdn.net/weixin_43660917/article/details/125317841
     *  [2] Java8 集合Map/list排序 - CSDN - https://blog.csdn.net/WLPJLP/article/details/115395383 [推荐]
     *  [3] java 8 stream 自定义字段排序 List＜Map＞ List＜Object＞根据某个字段排序或者多个字段排序 - CSDN - https://blog.csdn.net/zhang19age_/article/details/124974347
     *  [4] JAVA8中对List＜map＜String,Object＞＞根据map某个value值进行排序，还支持中文排序，可以替代order by - CSDN - https://onefire.blog.csdn.net/article/details/121710805 [推荐]
     *  [5] Java8实现对List＜Map＜String,Object＞＞多条件排序--中享思途 - CSDN - http://www.situedu.com/news/uid/3719.html [推荐]
     */
    public static <V> void sortByMapKey(List<Map<String, V>> listMap, String key){
        listMap.sort( (mapA, mapB) -> {
            String mapAKey = (String) mapA.get(key);
            String mapBKey = (String) mapB.get(key);
            return mapAKey.compareToIgnoreCase(mapBKey);// compareToIgnoreCase : 不区分字母大小写
        } );
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 将Collection集合对象转为数组
     *
     * @param <T>
     * @return null
     * array(size>0)
     */
    public static <T> List collectionToList(Collection<T> collection) {
        if (collection == null || collection.size() < 1) {
            LOG.error("Fail to invoke!Because collection is null or its size<1!");
            return null;
        }
        Iterator<T> iterator = collection.iterator();
        List<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
//        int size=collection.size();
//        T [] array = (T[]) new Object[size];//初始化泛型数组
//        for(int i=0;iterator.hasNext();i++){
//            array[i] = iterator.next();
//        }
        return list;
    }

    /**
     * 当前对象是否该列表里面的成员
     *
     * @param list
     * @param item
     */
    public static <T> boolean isItemInList(List<T> list, T item) {
        if (list == null) {
            return false;
        }
        Iterator iterator = null;
        iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * array to list
     * 注意事项: Arrays.asList()返回的对象是Arrays的内部类，对list的操作仍然反映在原数组上;故list是定长的，不支持add、remove操作
     * @param array
     * @param <T>
     * @return
     */
    //public static <T> List<T> arrayToList(T ... array)
    public static <T> List<T> arrayToList(T [] array){
        return Arrays.asList(array);
    }

    public static <T> Object[] listToArray(List<T> list){
        return list.toArray();
    }
}
