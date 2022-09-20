package cn.johnnyzen.common.util.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  01:39:12
 * @description: print tool for debug
 */
public class Print {
    private static final Logger LOG = LoggerFactory.getLogger(Print.class);

    private static final String BREAK_LINE_CHAR = "\n";

    /**
     * @param isOuput 是否立即打印(用于调试时控制)
     * @param object
     * @param <T>
     */
    public static <T> void print(boolean isOuput,T object){
        if(isOuput){
            LOG.debug(object.toString());
        }
    }

    /**
     *
     * @param collection
     * @param <T>
     *
     * [demo]
     *  Print.<T>printCollection(collection);
     */
    public static <T> void print(Collection collection){
        if(collection==null || collection.size()<1){
            LOG.warn("collection is null or its size < 1!");
            return;
        }
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()){
            LOG.debug(iterator.next().toString());
        }
    }
    public static <T> void print(T object){
        LOG.debug(object.toString());
    }

    public static <T> void print(List<T> list){
        print(list.toArray(), true);
    }

    public static <T> void println(){
        println("");
    }

    public static <T> void println(List<T> list){
        print(list.toArray(), "",true);
    }

    public static void println(String str){
        LOG.debug(str);
    }

    /**
     *
     * @param list
     * @param charAtLineEnd 行末尾符
     * @param <T>
     */
    public static <T> void print(List<T> list,String charAtLineEnd){
        print(list.toArray(),charAtLineEnd);
    }

    /**
     *
     * @param data
     * @param charAtLineEnd 行末尾符
     * @param <T>
     */
    public static <T> void print(T[] data, String charAtLineEnd, boolean isBreakLine){
        for(int i=0,size = data.length;i<size;i++){
            LOG.debug(data[i]+charAtLineEnd);
            if(isBreakLine){
                LOG.debug(BREAK_LINE_CHAR);
            }
        }
        LOG.debug(BREAK_LINE_CHAR);
    }

    public static <T> void print(T[] data, boolean isBreakLine){
        print(data, " ", isBreakLine);
    }

    public static <T> void print(T [] data,String charAtLineEnd){
        for(int i=0,size = data.length;i<size;i++){
            LOG.debug(data[i]+" "+(charAtLineEnd==null?"":charAtLineEnd));
        }
        LOG.debug(BREAK_LINE_CHAR);
    }

    public static <T> void print(T [][] data){
        for(int i=0,rows = data.length;i<rows;i++){
            for(int j=0,cols=data[i].length;j<rows;j++){
                LOG.debug(data[i][j] + "\t\t\t\t");
            }
            LOG.debug(BREAK_LINE_CHAR);
        }
    }

    public static void print(Map[] data){
        for(int i=0,rows = data.length;i<rows;i++){
            print(data[i]);
            LOG.debug(BREAK_LINE_CHAR);
        }
    }

    public static void print(Map data){
        Set set = data.keySet();
        Iterator iter = set.iterator();
        while(iter.hasNext()){
            Object key = iter.next();
            LOG.debug("<" + key + ":" + data.get(key) + ">" + "\t\t\t\t");
        }
    }

    public static <K, V> void println(Map<K, V> map){
        Set entrySet = map.entrySet();
        entrySet.forEach(entry -> {
            LOG.debug(entry.toString());
        });
    }
}
