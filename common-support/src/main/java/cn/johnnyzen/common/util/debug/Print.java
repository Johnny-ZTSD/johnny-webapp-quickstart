package cn.johnnyzen.common.util.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  01:39:12
 * @description: print tool for debug
 */
public class Print {
    private static final Logger logger = LoggerFactory.getLogger(Print.class);

    private static final String BREAK_LINE_CHAR = "\n";

    /**
     * @param isOuput 是否立即打印(用于调试时控制)
     * @param object
     * @param <T>
     */
    public static <T> void print(boolean isOuput,T object){
        if(isOuput){
            logger.debug(object.toString());
        }
    }

    public static <T> void printWithMessage(String message, T data){
        logger.debug(message);
        print(data);
    }



    public static <T> void print(T object){
        logger.debug(object.toString());
    }


    public static <T> void println(){
        println("");
    }



    public static void println(String str){
        logger.debug(str);
    }


    /**
     *
     * @param data
     * @param charAtLineEnd 行末尾符
     * @param <T>
     */
    public static <T> void print(T[] data, String charAtLineEnd, boolean isBreakLine){
        for(int i=0,size = data.length;i<size;i++){
            logger.debug(data[i]+charAtLineEnd);
            if(isBreakLine){
                logger.debug(BREAK_LINE_CHAR);
            }
        }
        logger.debug(BREAK_LINE_CHAR);
    }

    public static <T> void print(T[] data, boolean isBreakLine){
        print(data, " ", isBreakLine);
    }

    public static <T> void print(T [] data,String charAtLineEnd){
        for(int i=0,size = data.length;i<size;i++){
            logger.debug(data[i]+" "+(charAtLineEnd==null?"":charAtLineEnd));
        }
        logger.debug(BREAK_LINE_CHAR);
    }

    public static <T> void print(T [][] data){
        for(int i=0,rows = data.length;i<rows;i++){
            for(int j=0,cols=data[i].length;j<rows;j++){
                logger.debug(data[i][j] + "\t\t\t\t");
            }
            logger.debug(BREAK_LINE_CHAR);
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
            logger.warn("collection is null or its size < 1!");
            return;
        }
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()){
            logger.debug(iterator.next().toString());
        }
    }

    public static <T> void print(List<T> list){
        print(list.toArray(), true);
    }

    public static <T> void println(List<T> list){
        print(list.toArray(), "",true);
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

    public static <K, V> void println(Map<K, V> map){
        Set entrySet = map.entrySet();
        entrySet.forEach(entry -> {
            logger.debug(entry.toString());
        });
    }

    public static void print(Map[] data) {
        int i = 0;

        for (int rows = data.length; i < rows; ++i) {
            print(data[i]);
        }
    }

    public static void print(Map data) {
        Set set = data.keySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            Object key = iter.next();
            logger.debug("<" + key + ":" + data.get(key) + ">\t\t\t\t");
        }
    }

    public static void print(byte[] array){
        print(array, " ");
    }

    /**
     *
     * @param array 数组
     * @param delimiterCharacter 分割符
     */
    public static void print(byte[] array, String delimiterCharacter){
        if(array == null){
            return;
        }
        boolean delimiterCharEnabled = !StringUtils.isEmpty(delimiterCharacter);
        StringBuilder output = new StringBuilder();
        for(int i=0; i<array.length;i++){
            String byteStr = Byte.toString(array[i]);
            if(delimiterCharEnabled){
                //[comment] if byteArray=[1,104,99]; then `Byte.toString(byteArray[1])` => "104"
                output.append(byteStr);
                if(i+1 != array.length){
                    output.append(delimiterCharacter);
                }
            } else {
                print(byteStr);
            }
        }
        if(delimiterCharEnabled){
            logger.debug(output.toString());
        }
    }
}
