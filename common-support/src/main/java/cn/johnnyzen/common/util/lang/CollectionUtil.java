package cn.johnnyzen.common.util.lang;

import com.alibaba.fastjson.JSON;
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
     * map 转 2维数组
     * @return KVArrays
     *  KVArrays.keyArray : keySetArray
     *  KVArrays.valueArray : valueSetArray
     */
    public static <K, V> KVArrays mapToKVArrays(Map<K, V> map){
        KVArrays kvArrays = new KVArrays();
        LinkedList<K> keyArrayList = new LinkedList();
        LinkedList<V> valueArrayList = new LinkedList();
        map.entrySet().forEach(entry -> {
            keyArrayList.add(entry.getKey());
            valueArrayList.add(entry.getValue());
        });

        kvArrays = new KVArrays(keyArrayList.toArray(), valueArrayList.toArray());
        return kvArrays;
    }

    /**
     * 清除字符串数组中每个元素的首尾空格符
     * @param strArray
     * @return
     */
    public static String [] clearEmptyChar(String [] strArray){
        if(strArray == null){
            return null;
        }
        String [] newArray = new String [strArray.length];
        for(int i=0;i<strArray.length;i++){
            newArray[i] = strArray[i].trim();
        }
        return newArray;
    }

    /**
     * map 转 对象
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     * @reference-doc
     *  [1] Java Map转换实体类对象简单实现 - 博客园 - https://www.cnblogs.com/shisanye/p/13938282.html
     *  [2] java map转对象 - CSDN - https://blog.csdn.net/m0_67402774/article/details/126387146
     */
    public  static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if(map == null){
            return null;
        }
        // method 1: 不可靠(存在局限性)
        // org.apache.commons.beanutils.BeanUtils.populate(beanClass.newInstance(), map);
        // method 2: fastjson
        return JSON.parseObject(JSON.toJSONString(map), beanClass);
    }

    public static Map objectToMap(Object obj) {
        if(obj == null){
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    public static List enumerationToList(Enumeration enumeration){
        List list = new LinkedList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration);
        }
        return list;
    }

    public static <T extends Map> boolean isEmpty(T bean){
        if(bean == null){
            return true;
        }
        return bean.isEmpty();
    }

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
